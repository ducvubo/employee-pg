package com.pg.employee.config;


import com.pg.employee.grpc.EmployeeProto.EmployeeServiceGprcGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class GrpcClient {
    private final ManagedChannel channel;
    @Getter
    private final EmployeeServiceGprcGrpc.EmployeeServiceGprcBlockingStub blockingStub;

    public GrpcClient() {
        this.channel = ManagedChannelBuilder.forAddress("160.191.245.32", 8000)
                .usePlaintext()
                .build();
        this.blockingStub = EmployeeServiceGprcGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}