package cn.abstractmgs.controller;

import cn.abstractmgs.HelloRequest;
import cn.abstractmgs.MyServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QAController {
    @GrpcClient("myService")
    private MyServiceGrpc.MyServiceBlockingStub myServiceBlockingStub;

    @GetMapping("/api/qa")
    public String getAnswer(@RequestParam(value = "question") String question,
                            @RequestParam(value = "text") String text) {
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
