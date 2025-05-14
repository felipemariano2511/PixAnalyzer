package br.com.unicuritiba.pixanalyser.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PixKeyResponseDto {
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
