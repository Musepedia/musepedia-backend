package cn.abstractmgs.controller;

import cn.abstractmgs.HelloRequest;
import cn.abstractmgs.MyServiceGrpc;
import cn.abstractmgs.model.entity.Answer;
import cn.abstractmgs.service.RecommendQuestionService;
import cn.abstractmgs.service.TextService;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QAController {
    private int countOfRecommendation = 2;

    @GrpcClient("myService")
    private MyServiceGrpc.MyServiceBlockingStub myServiceBlockingStub;

    @Autowired
    private TextService textService;

    @Autowired
    private RecommendQuestionService recommendQuestionService;

    @GetMapping("api/qa")
    public Answer getAnswer(@RequestParam(value = "question") String question) {
        String text = textService.getText(question);
        String answer = null;
        int status = 0;

        if (text == null)
            answer = "暂时无法回答这个问题";
        else {
            HelloRequest helloRequest = HelloRequest
                    .newBuilder()
                    .setQuestion(question)
                    .setText(text)
                    .build();

            answer = myServiceBlockingStub
                    .sayHello(helloRequest)
                    .getAnswer();

            status = 1;
        }
        List<String> recommendQuestions = recommendQuestionService.getRandomQuestions(countOfRecommendation);

        Answer result = new Answer(status, answer, recommendQuestions);
        return result;
    }
}
