package br.com.unicuritiba.pixanalyser.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "pix_transactions")
public class PixTransaction {

    @Id
    private UUID id;

    private String endToEndId;

    private Long originClientId;

    private String originBank;

    private String destinationKeyValue;

    private String destinationBank;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    private String description;

    private LocalDateTime timestamp;
}
