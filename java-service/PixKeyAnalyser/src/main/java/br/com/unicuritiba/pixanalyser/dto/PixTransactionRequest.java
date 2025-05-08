package br.com.unicuritiba.pixanalyser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PixTransactionRequest {

    private String destinationKeyValue;
    private Long idClient;
    private BigDecimal amount;
    private String description;
}
