package br.com.unicuritiba.dictapi.dto;

public record PixKeyResponse(
        String keyType,
        String keyValue,
        AccountResponse account,
        OwnerResponse owner,
        String createdAt
) {}