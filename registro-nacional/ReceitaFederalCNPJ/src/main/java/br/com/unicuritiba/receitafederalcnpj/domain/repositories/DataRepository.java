package br.com.unicuritiba.receitafederalcnpj.domain.repositories;

import br.com.unicuritiba.receitafederalcnpj.domain.models.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataRepository extends JpaRepository<Data, Long> {
    Optional<Data> findByCnpj(String cnpj);
}
