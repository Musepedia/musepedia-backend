package cn.abstractmgs.controller;

import cn.abstractmgs.HelloRequest;
import cn.abstractmgs.MyServiceGrpc;
import cn.abstractmgs.service.TextService;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QAController {
    @GrpcClient("myService")
    private MyServiceGrpc.MyServiceBlockingStub myServiceBlockingStub;

    @Autowired()
    private TextService service;

    @GetMapping("/api/qa")
    public String getAnswer(@RequestParam(value = "question") String question) {
        String text = service.getText(question);

        if (text == null)
            return "暂时无法回答这个问题";

        HelloRequest helloRequest = HelloRequest
                .newBuilder()
                .setQuestion(question)
                .setText(text)
                .build();

        return myServiceBlockingStub
                .sayHello(helloRequest)
                .getAnswer();
    }
}
