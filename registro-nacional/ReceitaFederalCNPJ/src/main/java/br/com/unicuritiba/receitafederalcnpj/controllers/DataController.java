package br.com.unicuritiba.receitafederalcnpj.controllers;

import br.com.unicuritiba.receitafederalcnpj.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cnpj")
public class DataController {

    @Autowired
    private DataService service;

    @GetMapping("/{cnpj}")
    public ResponseEntity<?> searchCnpj(@PathVariable String cnpj) {
        return ResponseEntity.ok(service.searchCnpj(cnpj));
    }
}
