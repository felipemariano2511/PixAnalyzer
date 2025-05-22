package br.com.unicuritiba.pixanalyser.application.services;

import br.com.unicuritiba.pixanalyser.domain.models.InfoTransaction;
import br.com.unicuritiba.pixanalyser.domain.repositories.ClientRepository;
import br.com.unicuritiba.pixanalyser.dto.DictApiResponseDto;
import br.com.unicuritiba.pixanalyser.integrations.pix.PixKeyDictApiRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InfoTransactionService {

    @Autowired
    private PixKeyDictApiRestClient apiRestClient;

    @Autowired
    private ClientRepository clientRepository;

    public ResponseEntity<InfoTransaction> fetchTransactionInformation(String destinationKeyValue, Long originClientId) {

        DictApiResponseDto dataDictApi = apiRestClient.fetchKeyInformation(destinationKeyValue);

        InfoTransaction transaction = new InfoTransaction();
        transaction.setOriginClientId(originClientId);
        transaction.setOriginClientName(clientRepository.getNameById(originClientId));
        transaction.setBalance(clientRepository.getBalanceById(originClientId));
        transaction.setOriginBank(clientRepository.getOriginBankById(originClientId));
        transaction.setDestinationKeyValue(destinationKeyValue);
        transaction.setDestinationBank(dataDictApi.getInstitution());
        transaction.setTaxIdNumber(dataDictApi.getOwner().getTaxIdNumber());
        transaction.setReceiverName(dataDictApi.getOwner().getName());

        return ResponseEntity.ok(transaction);
    }
}
