package br.com.unicuritiba.dictapi.application.services;

import br.com.unicuritiba.dictapi.domain.models.PixKey;
import br.com.unicuritiba.dictapi.domain.repositories.PixKeyRepository;
import br.com.unicuritiba.dictapi.dto.PixKeyResponse;
import br.com.unicuritiba.dictapi.infrastructure.exceptions.NotFoundException;
import br.com.unicuritiba.dictapi.mappers.PixKeyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PixKeyService {

    @Autowired
    private PixKeyRepository repository;

    public ResponseEntity<PixKeyResponse> searchPixKey(String key) {
        var pixKey = repository.findByKey(key)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Chave Pix: %s não encontrada em nossa base de dados.", key)
                ));

        return ResponseEntity.ok(PixKeyMapper.toResponse(pixKey));
    }
}
