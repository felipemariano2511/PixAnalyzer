package br.com.unicuritiba.dadosgov.controllers;

import br.com.unicuritiba.dadosgov.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cpf")
public class DataController {

    @Autowired
    private DataService service;

    @GetMapping("/{cpf}")
    public ResponseEntity<?> findCpf(@PathVariable String cpf){
        return ResponseEntity.ok(service.getByCpf(cpf));
    }

}
