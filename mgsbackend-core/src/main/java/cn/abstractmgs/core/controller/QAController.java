package cn.abstractmgs.core.controller;

import cn.abstractmgs.common.model.BaseResponse;
import cn.abstractmgs.core.MyServiceGrpc;
import cn.abstractmgs.core.model.dto.AnswerDTO;
import cn.abstractmgs.core.service.QAService;
import cn.abstractmgs.core.service.RecommendQuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/api/qa")
@RequiredArgsConstructor
public class QAController {

    @GrpcClient("myService")
    private MyServiceGrpc.MyServiceBlockingStub myServiceBlockingStub;

    public static final Pattern placeholderPattern = Pattern.compile("[\\[A-Z\\]]");

    private final RecommendQuestionService recommendQuestionService;

    private final QAService qaService;

    @GetMapping
    public BaseResponse<AnswerDTO> getAnswer(@RequestParam String question) {
        String answer = qaService.getAnswer(question);
        int status = qaService.getStatus(answer);

        int countOfRecommendation = 2;
        List<String> recommendQuestions = recommendQuestionService.selectRecommendQuestions(question, answer);
        return BaseResponse.ok(new AnswerDTO(status, answer, recommendQuestions));
    }
}