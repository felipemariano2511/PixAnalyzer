package br.com.unicuritiba.pixanalyzer.domain.repositories;

import br.com.unicuritiba.pixanalyzer.domain.models.TransfersCount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface TransfersCountRepository extends JpaRepository<TransfersCount, UUID> {
    Optional<TransfersCount> findByDestinationKeyValue(String destinationKeyValue);
}
