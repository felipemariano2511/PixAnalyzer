package br.com.unicuritiba.pixanalyzer.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class ReceitaFederalCnpjResponseWrapperDto {

    private Map<String, String> headers;
    private ReceitaFederalCnpjResponseDto body;
    private int statusCodeValue;
    private String statusCode;
}
