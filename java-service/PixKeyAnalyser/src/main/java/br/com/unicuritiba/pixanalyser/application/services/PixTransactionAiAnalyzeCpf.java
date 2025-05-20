package br.com.unicuritiba.pixanalyser.application.services;

import br.com.unicuritiba.pixanalyser.AiAnalyzeCpfRequest;
import br.com.unicuritiba.pixanalyser.AiAnalyzeCpfResponse;
import br.com.unicuritiba.pixanalyser.integrations.ai.AiAnalyzeCpfGrpcClient;
import br.com.unicuritiba.pixanalyser.domain.models.PixTransaction;
import br.com.unicuritiba.pixanalyser.dto.DictApiResponseDto;
import br.com.unicuritiba.pixanalyser.dto.DadosGovResponseDto;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class PixTransactionAiAnalyzeCpf {

    private final AiAnalyzeCpfGrpcClient grpcClient;

    public PixTransactionAiAnalyzeCpf(AiAnalyzeCpfGrpcClient grpcClient) {
        this.grpcClient = grpcClient;
    }

    public AiAnalyzeCpfResponse analyzeCpf(DictApiResponseDto dictData,
                                         DadosGovResponseDto dataGov,
                                         PixTransaction transaction) {

        System.out.println("⚙️ Chamando método analyzeCpf");
        System.out.println("Dados dataGov = "  + dataGov.getCpf());
        System.out.println("🔍 dataGov.getRegistrationDate() = " + dataGov.getRegistrationDate());

        AiAnalyzeCpfRequest request = AiAnalyzeCpfRequest.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setOriginClientId(Math.toIntExact(transaction.getOriginClientId()))
                .setDestinationKeyValue(transaction.getDestinationKeyValue())
                .setAmount(transaction.getAmount().doubleValue())
                .setTimestamp(transaction.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .setKey(transaction.getDestinationKeyValue())
                .setKeyType(dictData.getKeyType())
                .setInstitution(dictData.getInstitution())
                .setBranch(dictData.getAccount().getBranch())
                .setAccountNumber(dictData.getAccount().getNumber())
                .setAccountType(dictData.getAccount().getType())
                .setOwnerType(dictData.getOwner().getType())
                .setOwnerName(dictData.getOwner().getName())
                .setCpf(dataGov.getCpf())
                .setFullName(dataGov.getFullName())
                .setBirthDate(dataGov.getBirthDate())
                .setRegistrationDate(dataGov.getRegistrationDate())
                .setStatus(dataGov.getStatus())
                .setCommonTransfersClient(5)
                .setAllTransfers(50337)
                .build();

        try {
            return grpcClient.analyze(request);
        } catch (Exception e) {
            System.err.println("❌ Erro ao chamar IA via gRPC: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao chamar serviço de IA", e);
        }
    }
}
