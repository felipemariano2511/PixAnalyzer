package br.com.unicuritiba.dadosgov.domain.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "data_cpf")
@Getter
@Setter
public class Data {

    @Id
    @GeneratedValue
    private UUID id;

    private String cpf;
    private String fullName;
    private Date birthDate;
    private Date registrationDate;
    private String status;
    private String checkDigits;

}
