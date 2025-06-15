package br.com.unicuritiba.keysfrontapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "keys_front")
public class KeysFront {

    @Id
    private UUID id;

    private String destinationKeyValue;
    private BigDecimal amount;
    private Date timestamp;
    private String keyType;
    private String institution;
    private String branch;
    private String accountNumber;
    private String accountType;
    private String ownerType;
    private String ownerName;
    private Date registrationDate;
    private String status;
    private String commonTransfersClient;
    private int allTransfers;
    private float confidenceScore;

}
