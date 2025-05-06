package br.com.unicuritiba.dadosgov.mappers;

import br.com.unicuritiba.dadosgov.dto.DataDTO;
import br.com.unicuritiba.dadosgov.domain.models.Data;

public class DataMapper {

    public static DataDTO toDTO(Data data) {
        if (data == null) return null;

        DataDTO dto = new DataDTO();
        dto.setCpf(data.getCpf());
        dto.setFullName(data.getFullName());
        dto.setBirthDate(data.getBirthDate());
        dto.setRegistrationDate(data.getRegistrationDate());
        dto.setStatus(data.getStatus());
        dto.setCheckDigits(data.getCheckDigits());

        return dto;
    }

    public static Data toEntity(DataDTO dto) {
        if (dto == null) return null;

        Data data = new Data();
        data.setCpf(dto.getCpf());
        data.setFullName(dto.getFullName());
        data.setBirthDate(dto.getBirthDate());
        data.setRegistrationDate(dto.getRegistrationDate());
        data.setStatus(dto.getStatus());
        data.setCheckDigits(dto.getCheckDigits());

        return data;
    }
}
