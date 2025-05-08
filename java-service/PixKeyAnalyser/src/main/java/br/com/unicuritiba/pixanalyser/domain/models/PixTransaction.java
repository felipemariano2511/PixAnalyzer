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
    @GeneratedValue
    private UUID id;

    @Column(name = "end_to_end_id", nullable = false, unique = true)
    private String endToEndId;

    @Column(name = "origin_client_id", nullable = false)
    private Long originClientId;

    @Column(name = "origin_bank", nullable = false, length = 20)
    private String originBank;

    @Column(name = "destination_key_value", nullable = false)
    private String destinationKeyValue;

    @Column(name = "destination_bank", nullable = false)
    private String destinationBank;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
}
