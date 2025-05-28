package br.com.unicuritiba.pixanalyzer.application.services;

import br.com.unicuritiba.pixanalyzer.AiAnalyzeCpfRequest;
import br.com.unicuritiba.pixanalyzer.AiAnalyzeCpfResponse;
import br.com.unicuritiba.pixanalyzer.domain.repositories.PixTransactionRepository;
import br.com.unicuritiba.pixanalyzer.domain.dto.AiAnalyzeRequestDto;
import br.com.unicuritiba.pixanalyzer.integrations.ai.AiAnalyzeCpfGrpcClient;
import br.com.unicuritiba.pixanalyzer.domain.dto.DictApiResponseDto;
import br.com.unicuritiba.pixanalyzer.domain.dto.DadosGovResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PixTransactionAiAnalyzeCpf {

    @Autowired
    private PixTransactionRepository pixTransactionRepository;

    private final AiAnalyzeCpfGrpcClient grpcClient;

    public PixTransactionAiAnalyzeCpf(AiAnalyzeCpfGrpcClient grpcClient) {
        this.grpcClient = grpcClient;
    }

    public AiAnalyzeCpfResponse analyzeCpf(DictApiResponseDto dictData,
                                           DadosGovResponseDto dataGov,
                                           AiAnalyzeRequestDto aiAnalyzeRequestDto) {
        try {
            AiAnalyzeCpfRequest request = AiAnalyzeCpfRequest.newBuilder()
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
                    .setCpf(dataGov.getCpf())
                    .setFullName(dataGov.getFullName())
                    .setBirthDate(dataGov.getBirthDate())
                    .setRegistrationDate(dataGov.getRegistrationDate())
                    .setStatus(dataGov.getStatus())
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
