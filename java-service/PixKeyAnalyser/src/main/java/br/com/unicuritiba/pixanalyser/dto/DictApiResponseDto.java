package br.com.unicuritiba.pixanalyser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DictApiResponseDto {
    @JsonProperty("key_type")
    private String keyType;

    @JsonProperty("key_value")
    private String keyValue;

    private Account account;

    private Owner owner;

    @JsonProperty("created_at")
    private String createdAt;

    public String getInstitution() {
        return account != null ? account.getInstitution() : null;
    }

    @Getter
    @Setter
    public static class Account {
        private String institution;
        private String branch;
        private String number;
        private String type;
    }

    @Getter
    @Setter
    public static class Owner {
        private String type;
        private String name;

        @JsonProperty("tax_id_number")
        private String taxIdNumber;
    }
}