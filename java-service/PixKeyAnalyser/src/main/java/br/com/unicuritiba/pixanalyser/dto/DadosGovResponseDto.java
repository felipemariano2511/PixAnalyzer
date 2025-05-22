package br.com.unicuritiba.pixanalyser.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DadosGovResponseDto {
    private String cpf;
    private String fullName;
    private String birthDate;
    private String registrationDate;
    private String status;
    private int checkDigits;
}
