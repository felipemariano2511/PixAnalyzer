package br.com.unicuritiba.keysfrontapi.mappers;

import br.com.unicuritiba.keysfrontapi.dto.KeysFrontResponseDTO;
import br.com.unicuritiba.keysfrontapi.models.KeysFront;
import org.springframework.stereotype.Component;

@Component
public class KeysFrontMapper {

    public KeysFrontResponseDTO toDTO(KeysFront entity) {
        if (entity == null) {
            return null;
        }

        return new KeysFrontResponseDTO(
                entity.getId(),
                entity.getDestinationKeyValue(),
                entity.getAmount(),
                entity.getTimestamp(),
                entity.getKeyType(),
                entity.getInstitution(),
                entity.getBranch(),
                entity.getAccountNumber(),
                entity.getAccountType(),
                entity.getOwnerType(),
                entity.getOwnerName(),
                entity.getRegistrationDate(),
                entity.getStatus(),
                entity.getCommonTransfersClient(),
                entity.getAllTransfers(),
                entity.getConfidenceScore()
        );
    }
}