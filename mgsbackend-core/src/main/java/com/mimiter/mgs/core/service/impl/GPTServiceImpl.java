package com.mimiter.mgs.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mimiter.mgs.core.repository.GPTCompletionRepository;
import com.mimiter.mgs.core.service.ExhibitService;
import com.mimiter.mgs.core.service.ExhibitTextService;
import com.mimiter.mgs.core.service.GPTService;
import com.mimiter.mgs.core.service.MuseumService;
import com.mimiter.mgs.gpt.GPTReply;
import com.mimiter.mgs.gpt.GPTRequest;
import com.mimiter.mgs.gpt.GPTServiceGrpc;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.ExhibitText;
import com.mimiter.mgs.model.entity.GPTCompletion;
import com.mimiter.mgs.model.entity.Museum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("gptService")
@RequiredArgsConstructor
public class GPTServiceImpl extends ServiceImpl<GPTCompletionRepository, GPTCompletion> implements GPTService {

    @Resource
    private MuseumService museumService;

    @Resource
    private ExhibitTextService exhibitTextService;

    @Resource
    private ExhibitService exhibitService;

    @GrpcClient("gptService")
    private GPTServiceGrpc.GPTServiceBlockingStub stub;

    @Override
    public GPTCompletion getGPTCompletion(String question, Long museumId, List<ExhibitText> exhibits) {
        var labelMap = new HashMap<Long, String>();
        var textMap = new HashMap<Long, List<String>>();
        Museum museum = museumService.getById(museumId);
        if (exhibits == null || exhibits.isEmpty() || museum == null) {
            log.warn("exhibits {} or museum {} not found", exhibits, museum);
            return null;
        }

        for (ExhibitText e : exhibits) {
            var id = e.getExhibitId();
            labelMap.computeIfAbsent(id, id2 -> {
                Exhibit ex = exhibitService.getById(id2);
                return ex == null ? "" : ex.getLabel();
            });

            List<String> texts = textMap.computeIfAbsent(id, k -> new ArrayList<>());
            texts.add(e.getText());
        }
        var rpcExhibits = exhibits.stream().map(e ->
                com.mimiter.mgs.gpt.Exhibit.newBuilder()
                        .setLabel(labelMap.get(e.getExhibitId()))
                        .addAllDescriptions(textMap.get(e.getExhibitId()))
                        .build()
        ).collect(Collectors.toList());

        GPTRequest request = GPTRequest
                .newBuilder()
                .setUserQuestion(question)
                .addAllExhibits(rpcExhibits)
                .setMuseumName(museum.getName())
                .build();
        try {

            GPTReply reply = stub.getAnswerWithGPT(request);
            GPTCompletion completion = new GPTCompletion();
            completion.setUserQuestion(question);
            completion.setPrompt(reply.getPrompt());
            completion.setCompletion(reply.getCompletion());
            completion.setPromptTokens(reply.getPromptTokens());
            completion.setCompletionTokens(reply.getCompletionTokens());
            return completion;
        } catch (Exception e) {
            log.error("gpt error: {} \n request is {}", e.getMessage(), requestToString(request));
        }

        return null;
    }

    @SuppressWarnings("All")
    private String requestToString(GPTRequest request) {
        return "{\n" +
                "\tuserQuestion: \"" + request.getUserQuestion() + "\",\n" +
                "\tmuseumName: \"" + request.getMuseumName() + "\"\n" +
                "}";
    }
}
