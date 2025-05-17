package br.com.unicuritiba.receitafederalcnpj.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataResponseDTO {

    private String cnpj;
    private String companyName;
    private String tradeName;
    private String registrationDate;
    private String legalNature;
    private String status;
    private String statusDate;
    private String statusReason;
    private ActivityDTO mainActivity;
    private List<ActivityDTO> secondaryActivities;
    private AddressDTO address;
    private String email;
    private String phone;
    private String shareCapital;
    private String branchType;

    @Getter
    @Setter
    public static class ActivityDTO {
        private String code;
        private String description;
    }

    @Getter
    @Setter
    public static class AddressDTO {
        private String street;
        private String number;
        private String complement;
        private String district;
        private String city;
        private String state;
        private String postalCode;
    }
}
