package br.com.unicuritiba.dadosgov.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class DataDTO {

    private UUID id;
    private String cpf;
    private String fullName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    private Date registrationDate;
    private String status;
    private String checkDigits;

}
