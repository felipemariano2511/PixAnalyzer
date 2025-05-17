package br.com.unicuritiba.pixanalyser.application.services;

import br.com.unicuritiba.pixanalyser.AiAnalyzeRequest;
import br.com.unicuritiba.pixanalyser.AiAnalyzeResponse;
import br.com.unicuritiba.pixanalyser.domain.models.PixTransaction;
import br.com.unicuritiba.pixanalyser.dto.DictApiResponseDto;
import br.com.unicuritiba.pixanalyser.dto.ReceitaFederalCnpjResponseDto;
import br.com.unicuritiba.pixanalyser.integrations.ai.AiAnalyzerGrpcClient;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class PixTransactionAiAnalyzer {

    private final AiAnalyzerGrpcClient grpcClient;

    public PixTransactionAiAnalyzer(AiAnalyzerGrpcClient grpcClient) {
        this.grpcClient = grpcClient;
    }

    public AiAnalyzeResponse analyzeCnpj(DictApiResponseDto dictData,
                                         ReceitaFederalCnpjResponseDto receitaData,
                                         PixTransaction transaction) {

        AiAnalyzeRequest request = AiAnalyzeRequest.newBuilder()
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
                .setStatus(receitaData.getStatus())
                .setShareCapital(Double.parseDouble(receitaData.getShareCapital()))
                .setBranchType(receitaData.getBranchType())
                .setCommonTransfersClient(0)
                .setAllTransfers(1576)
                .build();

        return grpcClient.analyze(request);
    }
}
