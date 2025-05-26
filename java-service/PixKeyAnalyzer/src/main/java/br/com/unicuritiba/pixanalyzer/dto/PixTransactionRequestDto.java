package br.com.unicuritiba.pixanalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PixTransactionRequestDto {

    private String destinationKeyValue;
    private Long originClientId;
    private BigDecimal amount;
    private String description;
}
