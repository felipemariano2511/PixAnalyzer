package br.com.unicuritiba.keysfrontapi.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public record KeysFrontResponseDTO(
        UUID id,
        String destinationKeyValue,
        BigDecimal amount,
        Date timestamp,
        String keyType,
        String institution,
        String branch,
        String accountNumber,
        String accountType,
        String ownerType,
        String ownerName,
        Date registrationDate,
        String status,
        String commonTransfersClient,
        int allTransfers,
        float confidenceScore
) {}
