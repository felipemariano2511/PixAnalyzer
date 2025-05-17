package br.com.unicuritiba.pixanalyser.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class PixKeyResponseDto {

    private List<Transfer> transfers;
    private AiAnalyse analysis;

    @Getter
    @Setter
    public static class Transfer {
        private String endToEndId;
        private Long originClientId;
        private String originBank;
        private String destinationKeyValue;
        private String receiverName;
        private String destinationBank;
        private String amount;
        private String description;
        private String timestamp;
    }

    @Getter
    @Setter
    public static class AiAnalyse {
        private double confidenceScore;
        private List<String> fraudReasons;
    }
}
