package br.com.unicuritiba.dadosgov.services;

import br.com.unicuritiba.dadosgov.infrastructure.exceptions.NotFoundException;

import br.com.unicuritiba.dadosgov.domain.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class DataService {

    @Autowired
    private DataRepository repository;


    public ResponseEntity<?> getByCpf(String cpf) {
        return ResponseEntity.ok(repository.findByCpf(cpf)
                .orElseThrow(()-> new NotFoundException(String.format("Não encontrado CPF %s em nossa base de dados.", cpf))));
    }

}
