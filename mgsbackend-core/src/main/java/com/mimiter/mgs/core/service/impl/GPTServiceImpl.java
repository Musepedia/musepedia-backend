package com.mimiter.mgs.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mimiter.mgs.common.exception.InternalException;
import com.mimiter.mgs.core.repository.GPTCompletionRepository;
import com.mimiter.mgs.core.service.ExhibitService;
import com.mimiter.mgs.core.service.ExhibitTextService;
import com.mimiter.mgs.core.service.GPTService;
import com.mimiter.mgs.core.service.MuseumService;
import com.mimiter.mgs.core.utils.SecurityUtil;
import com.mimiter.mgs.gpt.GPTReply;
import com.mimiter.mgs.gpt.GPTRequest;
import com.mimiter.mgs.gpt.GPTServiceGrpc;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.GPTCompletion;
import com.mimiter.mgs.model.entity.Museum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    public GPTCompletion getGPTCompletion(String question, Long museumId) {
        List<String> labels = exhibitTextService.getLabel(question, museumId);

        // todo 这里只使用第一个相关展品，后续支持多个展品
        if (labels.size() == 0) {
            // 问题中没有相关的展品
            return null;
        }
        Exhibit exhibit = exhibitService.selectExhibitByLabelAndMuseumId(labels.get(0), museumId);
        Museum museum = museumService.getById(museumId);

        GPTRequest request = GPTRequest
                .newBuilder()
                .setUserQuestion(question)
                .setExhibitLabel(exhibit.getLabel())
                .setExhibitDescription(exhibit.getDescription())
                .setMuseumName(museum.getName())
                .setMuseumDescription(museum.getDescription())
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
            log.error("gpt error: ", e);
        }

        return null;
    }
}
