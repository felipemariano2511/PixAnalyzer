package br.com.unicuritiba.receitafederalcnpj.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "data_cnpj")
@Getter
@Setter
public class Data {

    @Id
    @GeneratedValue
    private UUID id;

    private String cnpj;
    private String companyName;
    private String tradeName;
    private Date registrationDate;
    private String legalNature;
    private String status;
    private String statusDate;
    private String statusReason;
    private String primaryCode;
    private String primaryDescription;
    private String secondaryCode;
    private String secondaryDescription;
    private String street;
    private int number;
    private String complement;
    private String district;
    private String city;
    private String state;
    private String postalCode;
    private String email;
    private String phone;
    private int shareCapital;
    private String branchType;

}
