package cn.abstractmgs.core.controller;

import cn.abstractmgs.common.exception.BadRequestException;
import cn.abstractmgs.common.exception.ForbiddenException;
import cn.abstractmgs.common.exception.InternalException;
import cn.abstractmgs.common.model.BaseResponse;
import cn.abstractmgs.core.HelloReplyOrBuilder;
import cn.abstractmgs.core.HelloRequest;
import cn.abstractmgs.core.MyServiceGrpc;
import cn.abstractmgs.core.model.dto.AnswerDTO;
import cn.abstractmgs.core.service.RecommendQuestionService;
import cn.abstractmgs.core.service.TextService;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/qa")
public class QAController {

    @GrpcClient("myService")
    private MyServiceGrpc.MyServiceBlockingStub myServiceBlockingStub;

    @Autowired
    private TextService textService;

    @Autowired
    private RecommendQuestionService recommendQuestionService;

    @GetMapping
    public BaseResponse<AnswerDTO> getAnswer(@RequestParam String question) {
//        String text = textService.getText(question);
        String text = "try timeout";
        String answer = null;
        int status = 0;

        if (text == null){
            answer = "暂时无法回答这个问题";
        } else {
            HelloRequest helloRequest = HelloRequest
                    .newBuilder()
                    .setQuestion(question)
                    .setText(text)
                    .build();

            try {
                answer = myServiceBlockingStub
                        .sayHello(helloRequest)
                        .getAnswer();
            } catch (Exception e){
//                e.printStackTrace();
                log.error("Rpc error: ",e);
                throw new InternalException(""+e.getMessage());
            }

            status = 1;
        }
        int countOfRecommendation = 2;
        List<String> recommendQuestions = recommendQuestionService.getRandomQuestions(countOfRecommendation);
        return BaseResponse.ok(new AnswerDTO(status, answer, recommendQuestions));
    }
}