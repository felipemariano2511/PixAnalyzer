package br.com.unicuritiba.pixanalyser.integrations.ai;

import br.com.unicuritiba.pixanalyser.AiAnalyzeCpfRequest;
import br.com.unicuritiba.pixanalyser.AiAnalyzeCpfResponse;
import br.com.unicuritiba.pixanalyser.AiAnalyzeCpfServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AiAnalyzeCpfGrpcClient {

    @Value("${grpc.server.host}")
    private String host;

    @Value("${grpc.server.port}")
    private int port;

    private AiAnalyzeCpfServiceGrpc.AiAnalyzeCpfServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        this.stub = AiAnalyzeCpfServiceGrpc.newBlockingStub(channel);
    }

    public AiAnalyzeCpfResponse analyze(AiAnalyzeCpfRequest request) {
        return stub.aiAnalyzeCpf(request);
    }
}

