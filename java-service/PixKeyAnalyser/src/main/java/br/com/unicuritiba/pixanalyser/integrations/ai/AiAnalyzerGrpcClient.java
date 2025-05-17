package br.com.unicuritiba.pixanalyser.integrations.ai;

import br.com.unicuritiba.pixanalyser.AiAnalyzeRequest;
import br.com.unicuritiba.pixanalyser.AiAnalyzeResponse;
import br.com.unicuritiba.pixanalyser.AiAnalyzeServiceGrpc;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class AiAnalyzerGrpcClient {

    @Value("${grpc.server.host}")
    private String host;

    @Value("${grpc.server.port}")
    private int port;

    private AiAnalyzeServiceGrpc.AiAnalyzeServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        this.stub = AiAnalyzeServiceGrpc.newBlockingStub(channel);
    }

    public AiAnalyzeResponse analyze(AiAnalyzeRequest request) {
        return stub.aiAnalyze(request);
    }
}

