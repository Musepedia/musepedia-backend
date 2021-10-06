package cn.abstractmgs.controller;

import cn.abstractmgs.HelloRequest;
import cn.abstractmgs.HelloWorldProto;
import cn.abstractmgs.MyServiceGrpc;
import io.grpc.ManagedChannel;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GrpcClient("myService")
    private MyServiceGrpc.MyServiceBlockingStub myServiceBlockingStub;

    @GetMapping("/api/hello")
    public String hello(){
        HelloRequest helloRequest = HelloRequest.newBuilder().setName("hello").build();
        return myServiceBlockingStub.sayHello(helloRequest).getMessage();
    }
}
