package br.com.unicuritiba.receitafederalcnpj.mappers;

import br.com.unicuritiba.receitafederalcnpj.dto.DataResponseDTO;
import br.com.unicuritiba.receitafederalcnpj.domain.models.Data;

import java.text.SimpleDateFormat;
import java.util.Collections;

public class DataMapper {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static DataResponseDTO toResponseDTO(Data data) {
        DataResponseDTO dto = new DataResponseDTO();

        dto.setCnpj(data.getCnpj());
        dto.setCompanyName(data.getCompanyName());
        dto.setTradeName(data.getTradeName());
        dto.setRegistrationDate(sdf.format(data.getRegistrationDate()));
        dto.setLegalNature(data.getLegalNature());
        dto.setStatus(data.getStatus());
        dto.setStatusDate(data.getStatusDate());
        dto.setStatusReason(data.getStatusReason());

        // Main Activity
        DataResponseDTO.ActivityDTO main = new DataResponseDTO.ActivityDTO();
        main.setCode(data.getPrimaryCode());
        main.setDescription(data.getPrimaryDescription());
        dto.setMainActivity(main);

        // Secondary Activities
        DataResponseDTO.ActivityDTO secondary = new DataResponseDTO.ActivityDTO();
        secondary.setCode(data.getSecondaryCode());
        secondary.setDescription(data.getSecondaryDescription());
        dto.setSecondaryActivities(Collections.singletonList(secondary));

        // Address
        DataResponseDTO.AddressDTO address = new DataResponseDTO.AddressDTO();
        address.setStreet(data.getStreet());
        address.setNumber(String.valueOf(data.getNumber()));
        address.setComplement(data.getComplement());
        address.setDistrict(data.getDistrict());
        address.setCity(data.getCity());
        address.setState(data.getState());
        address.setPostalCode(data.getPostalCode());
        dto.setAddress(address);

        dto.setEmail(data.getEmail());
        dto.setPhone(data.getPhone());
        dto.setShareCapital(String.format("%.2f", (double) data.getShareCapital()));
        dto.setBranchType(data.getBranchType());

        return dto;
    }
}
