package cn.abstractmgs.utils;

import cn.abstractmgs.HelloWorldProto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

//@Component
public class GrpcClientManager {

    public ManagedChannel getChannel(){
        return ManagedChannelBuilder.forAddress("127.0.0.1", 5555)
                .disableRetry()
                .idleTimeout(2, TimeUnit.SECONDS)
                .build();

    }
}
