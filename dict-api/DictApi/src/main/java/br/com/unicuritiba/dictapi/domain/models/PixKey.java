package br.com.unicuritiba.dictapi.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "pix_keys")
@Getter
@Setter
@NoArgsConstructor
public class PixKey {

    @Id
    @GeneratedValue
    private UUID id;

    private String keyType;
    private String key;
    private String institution;
    private String branch;
    private String accountNumber;
    private String accountType;
    private String ownerType;
    private String ownerName;
    private String taxIdNumber;
    private Instant createdAt;

    public PixKey(String keyType, String key, String institution, String branch,
                  String accountNumber, String accountType, String ownerType,
                  String ownerName, String taxIdNumber, Instant createdAt) {

        this.keyType = keyType;
        this.key = key;
        this.institution = institution;
        this.branch = branch;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.ownerType = ownerType;
        this.ownerName = ownerName;
        this.taxIdNumber = taxIdNumber;
        this.createdAt = createdAt;
    }
}
