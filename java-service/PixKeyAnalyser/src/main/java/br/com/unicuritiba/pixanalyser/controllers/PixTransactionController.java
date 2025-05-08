package br.com.unicuritiba.pixanalyser.controllers;

import br.com.unicuritiba.pixanalyser.application.services.PixTransactionService;
import br.com.unicuritiba.pixanalyser.dto.PixTransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transferencia/pix")
public class PixTransactionController {

    @Autowired
    private PixTransactionService service;

    @PostMapping
    public ResponseEntity<?> makePixTransfer(@RequestBody PixTransactionRequest body) {
        return ResponseEntity.ok(service.createTransaction(
                body.getDestinationKeyValue(),
                body.getIdClient(),
                body.getAmount(),
                body.getDescription()));
    }
}
