package br.com.unicuritiba.pixanalyser.application.services;

import br.com.unicuritiba.pixanalyser.AiAnalyzeResponse;
import br.com.unicuritiba.pixanalyser.application.utils.PixUtils;
import br.com.unicuritiba.pixanalyser.domain.models.PixTransaction;
import br.com.unicuritiba.pixanalyser.domain.repositories.PixTransactionRepository;
import br.com.unicuritiba.pixanalyser.dto.DadosGovResponseDto;
import br.com.unicuritiba.pixanalyser.dto.DictApiResponseDto;
import br.com.unicuritiba.pixanalyser.dto.PixKeyResponseDto;
import br.com.unicuritiba.pixanalyser.dto.ReceitaFederalCnpjResponseDto;
import br.com.unicuritiba.pixanalyser.integrations.data.PixKeyDadosGovRestClient;
import br.com.unicuritiba.pixanalyser.integrations.data.PixKeyReceitaFederalCnpjRestClient;
import br.com.unicuritiba.pixanalyser.integrations.pix.PixKeyDictApiRestClient;
import br.com.unicuritiba.pixanalyser.mappers.PixKeyResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PixTransactionService {

    @Autowired
    private PixTransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PixKeyDictApiRestClient apiRestClient;

    @Autowired
    private PixKeyDadosGovRestClient pixKeyDadosGovRestClient;

    @Autowired
    private PixKeyReceitaFederalCnpjRestClient pixKeyReceitaFederalCnpjRestClient;

    @Autowired
    private PixTransactionAiAnalyzer pixTransactionAiAnalyzer;

    public ResponseEntity<PixKeyResponseDto> createTransaction(String destinationKeyValue,
                                                               Long originClientId,
                                                               BigDecimal amount,
                                                               String description) {
        AiAnalyzeResponse aiAnalyzeResponse = null;

        DictApiResponseDto dataDictApi = apiRestClient.fetchKeyInformation(destinationKeyValue);

        String endToEndId = PixUtils.generateEndToEndId("60746948");

        PixTransaction transaction = new PixTransaction();
        transaction.setEndToEndId(endToEndId);
        transaction.setOriginClientId(originClientId);
        transaction.setOriginBank("BRADESCO S.A.");
        transaction.setDestinationKeyValue(destinationKeyValue);
        transaction.setDestinationBank(dataDictApi.getInstitution());
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setTimestamp(LocalDateTime.now());

        PixTransaction saved = transactionRepository.save(transaction);

        if (!dataDictApi.getKeyType().equals("CNPJ")) {
            DadosGovResponseDto dataDadosGovApi = pixKeyDadosGovRestClient.fetchCpfInformation(
                    dataDictApi.getOwner().getTaxIdNumber());

            // chamar proto para CPF (ainda não implementado)

        } else {
            ReceitaFederalCnpjResponseDto dataReceitaFederalCnpjApi = pixKeyReceitaFederalCnpjRestClient.fetchCnpjInformation(
                    dataDictApi.getOwner().getTaxIdNumber());

            aiAnalyzeResponse = pixTransactionAiAnalyzer.analyzeCnpj(dataDictApi, dataReceitaFederalCnpjApi, transaction);
        }

        String receiverName = dataDictApi.getOwner().getName();

        double confidence = aiAnalyzeResponse != null ? aiAnalyzeResponse.getConfidenceScore() : 0.0;
        List<String> reasons = aiAnalyzeResponse != null ? aiAnalyzeResponse.getFraudReasonsList() : List.of();

        return ResponseEntity.ok(PixKeyResponseMapper.toResponseDto(saved, receiverName, confidence, reasons));
    }
}
