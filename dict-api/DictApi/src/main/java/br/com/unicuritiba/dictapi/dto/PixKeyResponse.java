package br.com.unicuritiba.dictapi.dto;

public record PixKeyResponse(
        String key_type,
        String key_value,
        AccountResponse account,
        OwnerResponse owner,
        String created_at
) {}