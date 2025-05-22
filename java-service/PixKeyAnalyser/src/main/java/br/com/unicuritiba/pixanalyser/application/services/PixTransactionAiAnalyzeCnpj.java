package br.com.unicuritiba.pixanalyser.application.services;

import br.com.unicuritiba.pixanalyser.AiAnalyzeCnpjRequest;
import br.com.unicuritiba.pixanalyser.AiAnalyzeCnpjResponse;
import br.com.unicuritiba.pixanalyser.domain.repositories.PixTransactionRepository;
import br.com.unicuritiba.pixanalyser.dto.AiAnalyzeRequestDto;
import br.com.unicuritiba.pixanalyser.dto.DictApiResponseDto;
import br.com.unicuritiba.pixanalyser.dto.ReceitaFederalCnpjResponseDto;
import br.com.unicuritiba.pixanalyser.integrations.ai.AiAnalyzeCnpjGrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PixTransactionAiAnalyzeCnpj {

    @Autowired
    private PixTransactionRepository pixTransactionRepository;

    private final AiAnalyzeCnpjGrpcClient grpcClient;

    public PixTransactionAiAnalyzeCnpj(AiAnalyzeCnpjGrpcClient grpcClient) {
        this.grpcClient = grpcClient;
    }

    public AiAnalyzeCnpjResponse analyzeCnpj(DictApiResponseDto dictData,
                                         ReceitaFederalCnpjResponseDto receitaData,
                                         AiAnalyzeRequestDto aiAnalyzeRequestDto) {
        try {
            AiAnalyzeCnpjRequest request = AiAnalyzeCnpjRequest.newBuilder()
                    .setId(UUID.randomUUID().toString())
                    .setOriginClientId(Math.toIntExact(aiAnalyzeRequestDto.getOriginClientId()))
                    .setDestinationKeyValue(aiAnalyzeRequestDto.getDestinationKeyValue())
                    .setAmount(aiAnalyzeRequestDto.getAmount().doubleValue())
                    .setTimestamp(String.valueOf(LocalDateTime.now()))
                    .setKey(aiAnalyzeRequestDto.getDestinationKeyValue())
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
                    .setCommonTransfersClient(countCommonTransfers(aiAnalyzeRequestDto.getOriginClientId(), aiAnalyzeRequestDto.getDestinationKeyValue()))
                    .setAllTransfers(countAllTransfers(aiAnalyzeRequestDto.getDestinationKeyValue()))
                    .build();

                return grpcClient.analyze(request);

        } catch (Exception e) {
            System.err.println("❌ Erro ao chamar IA via gRPC: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao chamar serviço de IA", e);
        }
    }

    public Integer countCommonTransfers(Long id, String key) {
        return pixTransactionRepository.countByOriginClientIdAndDestinationKeyValue(id, key);
    }

    public Integer countAllTransfers(String key) {
        return pixTransactionRepository.countByDestinationKeyValue(key);
    }

}
