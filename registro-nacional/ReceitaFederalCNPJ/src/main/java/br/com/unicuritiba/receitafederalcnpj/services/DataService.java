package br.com.unicuritiba.receitafederalcnpj.services;

import br.com.unicuritiba.receitafederalcnpj.infrastructure.exceptions.NotFoundException;
import br.com.unicuritiba.receitafederalcnpj.domain.models.Data;
import br.com.unicuritiba.receitafederalcnpj.domain.repositories.DataRepository;
import br.com.unicuritiba.receitafederalcnpj.dto.DataResponseDTO;
import br.com.unicuritiba.receitafederalcnpj.mappers.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DataService {

    @Autowired
    private DataRepository repository;

    public ResponseEntity<DataResponseDTO> searchCnpj(String cnpj) {
        Data data = repository.findByCnpj(cnpj)
                .orElseThrow(() -> new NotFoundException(String.format("CNPJ %s não encontrado no Banco de Dados.", cnpj)));

        DataResponseDTO dto = DataMapper.toResponseDTO(data);
        return ResponseEntity.ok(dto);
    }
}
