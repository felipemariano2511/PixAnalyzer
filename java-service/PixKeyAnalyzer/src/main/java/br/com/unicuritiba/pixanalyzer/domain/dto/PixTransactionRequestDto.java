package br.com.unicuritiba.pixanalyzer.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PixTransactionRequestDto {

    private String destinationKeyValue;
    private Long originClientId;
    private BigDecimal amount;
    private String description;
}
