package br.com.unicuritiba.pixanalyser.application.services;

import br.com.unicuritiba.pixanalyser.AiAnalyzeCnpjResponse;
import br.com.unicuritiba.pixanalyser.AiAnalyzeCpfResponse;
import br.com.unicuritiba.pixanalyser.domain.repositories.ClientRepository;
import br.com.unicuritiba.pixanalyser.dto.*;
import br.com.unicuritiba.pixanalyser.integrations.data.PixKeyDadosGovRestClient;
import br.com.unicuritiba.pixanalyser.integrations.data.PixKeyReceitaFederalCnpjRestClient;
import br.com.unicuritiba.pixanalyser.integrations.pix.PixKeyDictApiRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AiAnalyzeService {
    @Autowired
    private PixTransactionAiAnalyzeCpf pixTransactionAiAnalyzeCpf;

    @Autowired
    private PixTransactionAiAnalyzeCnpj pixTransactionAiAnalyzeCnpj;

    @Autowired
    private PixKeyDictApiRestClient pixKeyDictApiRestClient;

    @Autowired
    private PixKeyDadosGovRestClient pixKeyDadosGovRestClient;

    @Autowired
    private PixKeyReceitaFederalCnpjRestClient pixKeyReceitaFederalCnpjRestClient;

    @Autowired
    private ClientRepository clientRepository;

    public ResponseEntity<AiAnalyzeResponseDto> aiAnalyzePixKey(String destinationKeyValue,
                                                                Long originClientId,
                                                                AiAnalyzeRequestDto aiAnalyzeRequest) {

        DictApiResponseDto dictApi = pixKeyDictApiRestClient.fetchKeyInformation(destinationKeyValue);

        AiAnalyzeResult result = isCpf(dictApi)
                ? analyzeCpf(dictApi, aiAnalyzeRequest)
                : analyzeCnpj(dictApi, aiAnalyzeRequest);

        AiAnalyzeResponseDto response = buildResponse(aiAnalyzeRequest, originClientId, dictApi, result);

        return ResponseEntity.ok(response);
    }

    private boolean isCpf(DictApiResponseDto dictApi) {
        return "PESSOA_FISICA".equals(dictApi.getOwner().getType());
    }

    private AiAnalyzeResult analyzeCpf(DictApiResponseDto dictApi, AiAnalyzeRequestDto request) {
        DadosGovResponseDto govData = pixKeyDadosGovRestClient.fetchCpfInformation(dictApi.getOwner().getTaxIdNumber());
        AiAnalyzeCpfResponse result = pixTransactionAiAnalyzeCpf.analyzeCpf(dictApi, govData, request);
        double confidence = result != null ? result.getConfidenceScore() : 0.0;
        List<String> reasons = result != null ? result.getFraudReasonsList() : List.of();
        return new AiAnalyzeResult(confidence, reasons, govData);
    }

    private AiAnalyzeResult analyzeCnpj(DictApiResponseDto dictApi, AiAnalyzeRequestDto request) {
        ReceitaFederalCnpjResponseDto cnpjData = pixKeyReceitaFederalCnpjRestClient.fetchCnpjInformation(dictApi.getOwner().getTaxIdNumber());
        AiAnalyzeCnpjResponse result = pixTransactionAiAnalyzeCnpj.analyzeCnpj(dictApi, cnpjData, request);
        double confidence = result != null ? result.getConfidenceScore() : 0.0;
        List<String> reasons = result != null ? result.getFraudReasonsList() : List.of();
        return new AiAnalyzeResult(confidence, reasons, cnpjData);
    }

    private AiAnalyzeResponseDto buildResponse(AiAnalyzeRequestDto request,
                                               Long originClientId,
                                               DictApiResponseDto dictApi,
                                               AiAnalyzeResult result) {
        AiAnalyzeResponseDto response = new AiAnalyzeResponseDto();

        AiAnalyzeResponseDto.TransactionInformation info = new AiAnalyzeResponseDto.TransactionInformation();
        info.setOriginClientName(clientRepository.getNameById(originClientId));
        info.setOriginBank(clientRepository.getOriginBankById(originClientId));
        info.setBalance(clientRepository.getBalanceById(originClientId));
        info.setDestinationKeyValue(request.getDestinationKeyValue());
        info.setDestinationBank(dictApi.getInstitution());
        info.setReceiverName(dictApi.getOwner().getName());
        info.setTaxIdNumber(dictApi.getOwner().getTaxIdNumber());

        AiAnalyzeResponseDto.AiAnalyze ai = new AiAnalyzeResponseDto.AiAnalyze();
        ai.setConfidenceScore(result.confidence);
        ai.setFraudReasons(result.reasons);

        response.setTransactionInformation(info);
        response.setAiAnalyze(ai);

        return response;
    }

    private static class AiAnalyzeResult {
        private final double confidence;
        private final List<String> reasons;
        private final Object externalData;

        public AiAnalyzeResult(double confidence, List<String> reasons, Object externalData) {
            this.confidence = confidence;
            this.reasons = reasons;
            this.externalData = externalData;
        }
    }
}
