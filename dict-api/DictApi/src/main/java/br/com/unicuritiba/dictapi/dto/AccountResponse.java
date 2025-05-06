package br.com.unicuritiba.dictapi.dto;

public record AccountResponse(
        String institution,
        String branch,
        String number,
        String type
) {}
