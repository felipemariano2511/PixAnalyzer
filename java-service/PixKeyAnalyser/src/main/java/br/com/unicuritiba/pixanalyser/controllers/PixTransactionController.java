package br.com.unicuritiba.pixanalyser.controllers;

import br.com.unicuritiba.pixanalyser.application.services.AiAnalyzeService;
import br.com.unicuritiba.pixanalyser.application.services.InfoTransactionService;
import br.com.unicuritiba.pixanalyser.application.services.PixTransactionService;
import br.com.unicuritiba.pixanalyser.dto.AiAnalyzeRequestDto;
import br.com.unicuritiba.pixanalyser.dto.PixTransactionRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transferencia/pix")
public class PixTransactionController {

    @Autowired
    private PixTransactionService transactionService;

    @Autowired
    private InfoTransactionService infoTransactionService;

    @Autowired
    private AiAnalyzeService aiAnalyzeService;

    @PostMapping("/info-chave-pix")
    public ResponseEntity<?> fetchTransactionInformation(@RequestBody PixTransactionRequestDto body) {
        return ResponseEntity.ok(infoTransactionService.fetchTransactionInformation(
                body.getDestinationKeyValue(),
                body.getOriginClientId()));
    }

    @PostMapping("/analisar")
    public ResponseEntity<?> aiAnalizePixKey(@RequestBody AiAnalyzeRequestDto body) {
        return ResponseEntity.ok(aiAnalyzeService.aiAnalyzePixKey(
                body.getDestinationKeyValue(),
                body.getOriginClientId(),
                body));
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> makePixTransfer(@RequestBody PixTransactionRequestDto body) {
        return ResponseEntity.ok(transactionService.createTransaction(
                body.getDestinationKeyValue(),
                body.getOriginClientId(),
                body.getAmount(),
                body.getDescription()));
    }
}
