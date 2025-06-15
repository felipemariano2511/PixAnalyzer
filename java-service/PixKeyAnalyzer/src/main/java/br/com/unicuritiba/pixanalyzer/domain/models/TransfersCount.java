package br.com.unicuritiba.pixanalyzer.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "transfers_count")
public class TransfersCount {

    @Id
    private UUID id;

    private String destinationKeyValue;
    private Long commonTransfers;
    private Long allTransfers;
}
