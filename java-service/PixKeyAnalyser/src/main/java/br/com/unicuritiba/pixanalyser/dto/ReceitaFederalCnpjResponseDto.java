package br.com.unicuritiba.pixanalyser.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ReceitaFederalCnpjResponseDto {

    private String cnpj;
    private String companyName;
    private String tradeName;
    private String registrationDate;
    private String legalNature;
    private String status;
    private String statusDate;
    private String statusReason;
    private Activities mainActivity;
    private List<Activities> secondaryActivities;
    private Address address;
    private String email;
    private String phone;
    private String shareCapital;
    private String branchType;

    @Getter
    @Setter
    public static class Activities {
        private String code;
        private String description;
    }

    @Getter
    @Setter
    public static class Address {
        private String street;
        private String number;
        private String complement;
        private String district;
        private String city;
        private String state;
        private String postalCode;
    }
}
