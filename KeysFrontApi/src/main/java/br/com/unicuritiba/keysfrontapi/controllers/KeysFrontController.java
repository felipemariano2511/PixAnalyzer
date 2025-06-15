package br.com.unicuritiba.keysfrontapi.controllers;


import br.com.unicuritiba.keysfrontapi.repositories.KeysFrontRepository;
import br.com.unicuritiba.keysfrontapi.services.KeysFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keys_front")
public class KeysFrontController {

    @Autowired
    KeysFrontService service;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
