package br.com.unicuritiba.pixanalyser.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class DadosGovResponseDto {
    private String cpf;
    private String fullName;
    private Date birthDate;
    private Date resgitrationDate;
    private String status;
    private int checkDigits;
}
