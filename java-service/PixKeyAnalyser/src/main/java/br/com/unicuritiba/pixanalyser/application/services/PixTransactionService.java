package br.com.unicuritiba.pixanalyser.application.services;

import br.com.unicuritiba.pixanalyser.application.utils.PixUtils;
import br.com.unicuritiba.pixanalyser.domain.models.PixTransaction;
import br.com.unicuritiba.pixanalyser.domain.repositories.ClientRepository;
import br.com.unicuritiba.pixanalyser.dto.DictApiResponseDto;
import br.com.unicuritiba.pixanalyser.integrations.pix.PixKeyDictApiRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PixTransactionService {

    @Autowired
    private PixKeyDictApiRestClient apiRestClient;

    @Autowired
    private ClientRepository clientRepository;

    public ResponseEntity<PixTransaction> createTransaction(String destinationKeyValue,
                                                                       Long originClientId,
                                                                       BigDecimal amount,
                                                                       String description) {

        DictApiResponseDto dataDictApi = apiRestClient.fetchKeyInformation(destinationKeyValue);

        String endToEndId = PixUtils.generateEndToEndId("60746948");

        PixTransaction transaction = new PixTransaction();
        transaction.setId(UUID.randomUUID());
        transaction.setEndToEndId(endToEndId);
        transaction.setOriginClientId(originClientId);
        transaction.setOriginBank(clientRepository.getOriginBankById(originClientId));
        transaction.setDestinationKeyValue(destinationKeyValue);
        transaction.setDestinationBank(dataDictApi.getInstitution());
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setTimestamp(LocalDateTime.now());

        return ResponseEntity.ok(transaction);
    }
}
