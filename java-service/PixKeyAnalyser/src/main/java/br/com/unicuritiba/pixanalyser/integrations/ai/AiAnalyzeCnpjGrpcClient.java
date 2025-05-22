package br.com.unicuritiba.pixanalyser.integrations.ai;

import br.com.unicuritiba.pixanalyser.AiAnalyzeCnpjRequest;
import br.com.unicuritiba.pixanalyser.AiAnalyzeCnpjResponse;
import br.com.unicuritiba.pixanalyser.AiAnalyzeCnpjServiceGrpc;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class AiAnalyzeCnpjGrpcClient {

    @Value("${grpc.server.host}")
    private String host;

    @Value("${grpc.server.port}")
    private int port;

    private AiAnalyzeCnpjServiceGrpc.AiAnalyzeCnpjServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        this.stub = AiAnalyzeCnpjServiceGrpc.newBlockingStub(channel);
    }

    public AiAnalyzeCnpjResponse analyze(AiAnalyzeCnpjRequest request) {
        return stub.aiAnalyzeCnpj(request);
    }
}

