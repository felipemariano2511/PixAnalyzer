package br.com.unicuritiba.dadosgov.domain.repositories;

import br.com.unicuritiba.dadosgov.domain.models.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataRepository extends JpaRepository<Data, Long> {

    Optional<Data> findByCpf(String cpf);
}
