package br.com.unicuritiba.pixanalyzer.domain.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class AiAnalyzeRequestDto {
    private String destinationKeyValue;
    private Long originClientId;
    private BigDecimal amount;
    private Timestamp timestamp;
}
