package br.com.unicuritiba.receitafederalcnpj.services;

import br.com.unicuritiba.dadosgov.infrastructure.exceptions.NotFoundException;
import br.com.unicuritiba.receitafederalcnpj.domain.models.Data;
import br.com.unicuritiba.receitafederalcnpj.domain.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DataService {
    @Autowired
    private DataRepository repository;

    public ResponseEntity<Data> searchCnpj(String cnpj) {
        return ResponseEntity.ok(repository.findByCnpj(cnpj)
                .orElseThrow(() -> new NotFoundException(String.format("CNPJ %s não encontrado no Banco de Dados.",cnpj))));
    }

}
