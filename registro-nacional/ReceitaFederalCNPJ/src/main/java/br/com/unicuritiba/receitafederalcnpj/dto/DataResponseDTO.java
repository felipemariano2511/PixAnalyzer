package br.com.unicuritiba.receitafederalcnpj.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataResponseDTO {

    @JsonProperty("cnpj")
    private String cnpj;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("trade_name")
    private String tradeName;

    @JsonProperty("registration_date")
    private String registrationDate;

    @JsonProperty("legal_nature")
    private String legalNature;

    @JsonProperty("status")
    private String status;

    @JsonProperty("status_date")
    private String statusDate;

    @JsonProperty("status_reason")
    private String statusReason;

    @JsonProperty("main_activity")
    private ActivityDTO mainActivity;

    @JsonProperty("secondary_activities")
    private List<ActivityDTO> secondaryActivities;

    @JsonProperty("address")
    private AddressDTO address;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("share_capital")
    private String shareCapital;

    @JsonProperty("branch_type")
    private String branchType;

    @Getter
    @Setter
    public static class ActivityDTO {
        @JsonProperty("code")
        private String code;

        @JsonProperty("description")
        private String description;
    }

    @Getter
    @Setter
    public static class AddressDTO {
        @JsonProperty("street")
        private String street;

        @JsonProperty("number")
        private String number;

        @JsonProperty("complement")
        private String complement;

        @JsonProperty("district")
        private String district;

        @JsonProperty("city")
        private String city;

        @JsonProperty("state")
        private String state;

        @JsonProperty("postal_code")
        private String postalCode;
    }
}
