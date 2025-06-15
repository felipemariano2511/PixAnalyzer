package br.com.unicuritiba.keysfrontapi.repositories;

import br.com.unicuritiba.keysfrontapi.models.KeysFront;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface KeysFrontRepository extends JpaRepository<KeysFront, UUID> {
}
