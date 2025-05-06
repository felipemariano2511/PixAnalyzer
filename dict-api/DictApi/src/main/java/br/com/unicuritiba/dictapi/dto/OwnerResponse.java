package br.com.unicuritiba.dictapi.dto;

public record OwnerResponse(
        String type,
        String name,
        String tax_id_number
) {}