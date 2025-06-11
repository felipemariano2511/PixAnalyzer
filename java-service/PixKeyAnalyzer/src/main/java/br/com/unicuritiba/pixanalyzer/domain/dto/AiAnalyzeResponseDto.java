package br.com.unicuritiba.pixanalyzer.domain.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AiAnalyzeResponseDto {

    private TransactionInformation transactionInformation;
    private AiAnalyze aiAnalyze;

    @Getter
    @Setter
    public static class TransactionInformation {
        private String originClientName;
        private String originBank;
        private BigDecimal balance;
        private String destinationKeyValue;
        private String destinationBank;
        private String receiverName;
        private String taxIdNumber;
    }

    @Getter
    @Setter
    public static class AiAnalyze {
        private double confidenceScore;
        private List<String> fraudReasons;
    }

}
