package br.com.unicuritiba.dictapi.dto;

public record OwnerResponse(
        String type,
        String name,
        String taxIdNumber
) {}