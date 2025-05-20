package br.com.unicuritiba.pixanalyser.application.services;

import br.com.unicuritiba.pixanalyser.AiAnalyzeCnpjRequest;
import br.com.unicuritiba.pixanalyser.AiAnalyzeCnpjResponse;
import br.com.unicuritiba.pixanalyser.domain.models.PixTransaction;
import br.com.unicuritiba.pixanalyser.dto.DictApiResponseDto;
import br.com.unicuritiba.pixanalyser.dto.ReceitaFederalCnpjResponseDto;
import br.com.unicuritiba.pixanalyser.integrations.ai.AiAnalyzeCnpjGrpcClient;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class PixTransactionAiAnalyzeCnpj {

    private final AiAnalyzeCnpjGrpcClient grpcClient;

    public PixTransactionAiAnalyzeCnpj(AiAnalyzeCnpjGrpcClient grpcClient) {
        this.grpcClient = grpcClient;
    }

    public AiAnalyzeCnpjResponse analyzeCnpj(DictApiResponseDto dictData,
                                         ReceitaFederalCnpjResponseDto receitaData,
                                         PixTransaction transaction) {

        System.out.println("⚙️ Chamando método analyzeCnpj");

        System.out.println("🔍 receitaData.getShareCapital() = " + receitaData.getShareCapital());
        System.out.println("🔍 dictData.getAccount() = " + dictData.getAccount());
        System.out.println("🔍 dictData.getOwner() = " + dictData.getOwner());
        System.out.println("endToendId " + transaction.getEndToEndId());

        AiAnalyzeCnpjRequest request = AiAnalyzeCnpjRequest.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setEndToEndId(transaction.getEndToEndId())
                .setOriginClientId(String.valueOf(transaction.getOriginClientId()))
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
                .setCnpj(receitaData.getCnpj())
                .setCompanyName(receitaData.getCompanyName())
                .setRegistrationDate(receitaData.getRegistrationDate())
                .setStatus(receitaData.getStatus())
                .setStatusDate(receitaData.getStatusDate())
                .setShareCapital(Double.parseDouble(receitaData.getShareCapital()))
                .setBranchType(receitaData.getBranchType())
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
