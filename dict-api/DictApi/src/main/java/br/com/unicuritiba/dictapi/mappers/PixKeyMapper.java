package br.com.unicuritiba.dictapi.mappers;

import br.com.unicuritiba.dictapi.domain.models.PixKey;
import br.com.unicuritiba.dictapi.dto.AccountResponse;
import br.com.unicuritiba.dictapi.dto.OwnerResponse;
import br.com.unicuritiba.dictapi.dto.PixKeyResponse;
import java.time.format.DateTimeFormatter;

public class PixKeyMapper {
    public static PixKeyResponse toResponse(PixKey entity) {
        return new PixKeyResponse(
                entity.getKeyType(),
                entity.getKey(),
                new AccountResponse(
                        entity.getInstitution(),
                        entity.getBranch(),
                        entity.getAccountNumber(),
                        entity.getAccountType()
                ),
                new OwnerResponse(
                        entity.getOwnerType(),
                        entity.getOwnerName(),
                        entity.getTaxIdNumber()
                ),
                DateTimeFormatter.ISO_INSTANT.format(entity.getCreatedAt())
        );
    }
}
