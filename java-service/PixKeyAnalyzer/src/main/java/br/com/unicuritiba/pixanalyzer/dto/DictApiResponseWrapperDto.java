package br.com.unicuritiba.pixanalyzer.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class DictApiResponseWrapperDto {

    private Map<String, String> headers;
    private DictApiResponseDto body;
    private int statusCodeValue;
    private String statusCode;
}
