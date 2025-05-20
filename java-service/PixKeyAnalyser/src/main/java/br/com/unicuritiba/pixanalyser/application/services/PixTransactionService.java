package br.com.unicuritiba.pixanalyser.application.services;

import br.com.unicuritiba.pixanalyser.AiAnalyzeCnpjResponse;
import br.com.unicuritiba.pixanalyser.AiAnalyzeCpfResponse;
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
    private PixTransactionAiAnalyzeCnpj pixTransactionAiAnalyzeCnpj;

    @Autowired
    private PixTransactionAiAnalyzeCpf pixTransactionAiAnalyzeCpf;

    public ResponseEntity<PixKeyResponseDto> createTransaction(String destinationKeyValue,
                                                               Long originClientId,
                                                               BigDecimal amount,
                                                               String description) {
        AiAnalyzeCnpjResponse aiAnalyzeCnpjResponse = null;
        AiAnalyzeCpfResponse aiAnalyzeCpfResponse = null;
        double confidence = 0;
        List<String> reasons;


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

        System.out.println("Tipo da chave pix: " + dataDictApi.getKeyType());
        System.out.println("Tipo da chave pix: " + dataDictApi.getOwner().getTaxIdNumber());

        if (!dataDictApi.getKeyType().equals("CNPJ")) {
            DadosGovResponseDto dataDadosGovApi = pixKeyDadosGovRestClient.fetchCpfInformation(
                    dataDictApi.getOwner().getTaxIdNumber());

            System.out.println("teste de dados Gov = " + dataDadosGovApi.getCpf());

            System.out.println("Entrou cpf");

            aiAnalyzeCpfResponse = pixTransactionAiAnalyzeCpf.analyzeCpf(dataDictApi, dataDadosGovApi, transaction);

            confidence = aiAnalyzeCpfResponse != null ? aiAnalyzeCpfResponse.getConfidenceScore() : 0.0;
            reasons = aiAnalyzeCpfResponse != null ? aiAnalyzeCpfResponse.getFraudReasonsList() : List.of();

        } else {
            ReceitaFederalCnpjResponseDto dataReceitaFederalCnpjApi = pixKeyReceitaFederalCnpjRestClient.fetchCnpjInformation(
                    dataDictApi.getOwner().getTaxIdNumber());

            System.out.println("Entrou cnpj");

            aiAnalyzeCnpjResponse = pixTransactionAiAnalyzeCnpj.analyzeCnpj(dataDictApi, dataReceitaFederalCnpjApi, transaction);

           confidence = aiAnalyzeCnpjResponse != null ? aiAnalyzeCnpjResponse.getConfidenceScore() : 0.0;
           reasons = aiAnalyzeCnpjResponse != null ? aiAnalyzeCnpjResponse.getFraudReasonsList() : List.of();
        }

        System.out.println("Entrou no retorno");

        String receiverName = dataDictApi.getOwner().getName();



        return ResponseEntity.ok(PixKeyResponseMapper.toResponseDto(saved, receiverName, confidence, reasons));
    }
}
