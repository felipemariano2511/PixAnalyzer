package br.com.unicuritiba.dadosgov.models;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "data")
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Date registrationDate;
    private String cpf;
    private String cnpj;

}
