package br.com.unicuritiba.dadosgov.services;

import br.com.unicuritiba.dadosgov.domain.models.Data;
import br.com.unicuritiba.dadosgov.dto.DataDTO;
import br.com.unicuritiba.dadosgov.infrastructure.exceptions.NotFoundException;
import br.com.unicuritiba.dadosgov.domain.repositories.DataRepository;
import br.com.unicuritiba.dadosgov.mappers.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class DataService {

    @Autowired
    private DataRepository repository;


    public ResponseEntity<DataDTO> getByCpf(String cpf) {
        var cpfData = repository.findByCpf(cpf)
                .orElseThrow(() -> new NotFoundException(
                        String.format("O CPF %s não foi encontrado em nossa base de dados.", cpf)
                ));

        return ResponseEntity.ok(DataMapper.toDTO(cpfData));
    }

}
