package br.com.unicuritiba.pixanalyser.domain.models;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class InfoTransaction {

    private Long originClientId;
    private String originClientName;
    private String originBank;
    private BigDecimal balance;
    private String destinationKeyValue;
    private String destinationBank;
    private String receiverName;
    private String taxIdNumber;
}
