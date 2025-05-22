package br.com.unicuritiba.pixanalyser.domain.repositories;

import br.com.unicuritiba.pixanalyser.domain.models.PixTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PixTransactionRepository extends JpaRepository<PixTransaction, UUID> {
    int countByOriginClientIdAndDestinationKeyValue(Long originClientId, String destinationKeyValue);
    int countByDestinationKeyValue(String destinationKeyValue);
}
