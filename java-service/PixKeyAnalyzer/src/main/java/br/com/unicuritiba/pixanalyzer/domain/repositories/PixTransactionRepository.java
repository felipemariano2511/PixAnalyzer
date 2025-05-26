package br.com.unicuritiba.pixanalyzer.domain.repositories;

import br.com.unicuritiba.pixanalyzer.domain.models.PixTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PixTransactionRepository extends JpaRepository<PixTransaction, UUID> {
    int countByOriginClientIdAndDestinationKeyValue(Long originClientId, String destinationKeyValue);
    int countByDestinationKeyValue(String destinationKeyValue);
}
