package br.com.unicuritiba.pixanalyser.application.services;

import br.com.unicuritiba.pixanalyser.application.utils.PixUtils;
import br.com.unicuritiba.pixanalyser.domain.models.PixTransaction;
import br.com.unicuritiba.pixanalyser.domain.repositories.PixTransactionRepository;
import br.com.unicuritiba.pixanalyser.dto.PixKeyResponse;
import br.com.unicuritiba.pixanalyser.dto.PixKeyResponseWrapper;
import br.com.unicuritiba.pixanalyser.infrastructure.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@Service
public class PixTransactionService {

    @Autowired
    private PixTransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<PixTransaction> createTransaction(String destinationKeyValue,
                                                            Long originClientId,
                                                            BigDecimal amount,
                                                            String description) {

        PixKeyResponse pixKey;

        try {
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("key", destinationKeyValue);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<PixKeyResponseWrapper> response = restTemplate.exchange(
                    "http://dict-api-container:8081/api/dict/keys",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<PixKeyResponseWrapper>() {}
            );

            pixKey = response.getBody().getBody();

            if (pixKey == null || pixKey.getInstitution() == null) {
                throw new NotFoundException("Chave Pix não encontrada!");
            }

        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException(String.format(
                    "Chave Pix: %s não encontrada na base de dados do DICT.", destinationKeyValue));
        }

        String endToEndId = PixUtils.generateEndToEndId("60746948");

        PixTransaction transaction = new PixTransaction();
        transaction.setEndToEndId(endToEndId);
        transaction.setOriginClientId(originClientId);
        transaction.setOriginBank("BRADESCO S.A.");
        transaction.setDestinationKeyValue(destinationKeyValue);
        transaction.setDestinationBank(pixKey.getInstitution());
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setTimestamp(LocalDateTime.now());

        return ResponseEntity.ok(transactionRepository.save(transaction));
    }
}