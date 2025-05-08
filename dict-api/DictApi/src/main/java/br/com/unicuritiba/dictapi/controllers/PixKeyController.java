package br.com.unicuritiba.dictapi.controllers;

import br.com.unicuritiba.dictapi.application.services.PixKeyService;
import br.com.unicuritiba.dictapi.dto.PixKeyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dict/keys")
public class PixKeyController {

    @Autowired
    private PixKeyService service;

    @PostMapping
    public ResponseEntity<?> searchPixKey(@RequestBody PixKeyRequest request) {
        return ResponseEntity.ok(service.searchPixKey(request.getKey()));
    }
}
