package br.com.unicuritiba.dictapi.domain.repositories;

import br.com.unicuritiba.dictapi.domain.models.PixKey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface PixKeyRepository extends JpaRepository<PixKey, UUID> {
    Optional<PixKey> findByKey(String key);
}
