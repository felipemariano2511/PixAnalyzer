package br.com.unicuritiba.keysfrontapi.services;

import br.com.unicuritiba.keysfrontapi.dto.KeysFrontResponseDTO;
import br.com.unicuritiba.keysfrontapi.mappers.KeysFrontMapper;
import br.com.unicuritiba.keysfrontapi.models.KeysFront;
import br.com.unicuritiba.keysfrontapi.repositories.KeysFrontRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KeysFrontService {

    @Autowired
    private KeysFrontRepository repository;

    @Autowired
    private KeysFrontMapper mapper;

    public ResponseEntity<List<KeysFrontResponseDTO>> getAll(){
        return ResponseEntity.ok(repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList());
    }
}
