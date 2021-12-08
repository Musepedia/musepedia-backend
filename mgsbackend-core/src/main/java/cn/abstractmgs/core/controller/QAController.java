package cn.abstractmgs.core.controller;

import cn.abstractmgs.common.model.BaseResponse;
import cn.abstractmgs.core.HelloRequest;
import cn.abstractmgs.core.MyServiceGrpc;
import cn.abstractmgs.core.model.dto.AnswerDTO;
import cn.abstractmgs.core.service.RecommendQuestionService;
import cn.abstractmgs.core.service.ExhibitTextService;
import com.google.protobuf.Message;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/api/qa")
public class QAController {

    @GrpcClient("myService")
    private MyServiceGrpc.MyServiceBlockingStub myServiceBlockingStub;

    private final Pattern placeholderPattern = Pattern.compile("[\\[A-Z\\]]");

    @Autowired
    private ExhibitTextService exhibitTextService;

    @Autowired
    private RecommendQuestionService recommendQuestionService;

    @GetMapping
    public BaseResponse<AnswerDTO> getAnswer(@RequestParam String question) {
        List<String> texts = exhibitTextService.getAllTexts(question);
        String answer = "暂时无法回答这个问题";
        int status = 0;
        System.out.println(texts);
        if (texts != null) {
            HelloRequest helloRequest = HelloRequest
                    .newBuilder()
                    .setQuestion(question)
                    .addAllTexts(texts)
                    .build();

            try {
                String resp = myServiceBlockingStub
                        .sayHello(helloRequest)
                        .getAnswer();
                // 替换grpc返回的所有包含[CLS]的占位符，如果仅包含占位符则返回"无法回答"
                resp = placeholderPattern.matcher(resp).replaceAll("");
                if(resp.length() != 0){
                    // has exact answer
                    answer = resp;
                    status = 1;
                }
            } catch (Exception e){
                // rpc error
                log.error("Rpc error: ",e);
            }
        }
        int countOfRecommendation = 2;
        List<String> recommendQuestions = recommendQuestionService.getRandomQuestions(countOfRecommendation);
        return BaseResponse.ok(new AnswerDTO(status, answer, recommendQuestions));
    }
}