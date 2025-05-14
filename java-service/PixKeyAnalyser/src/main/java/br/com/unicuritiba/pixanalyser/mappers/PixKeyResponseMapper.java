package br.com.unicuritiba.pixanalyser.mappers;

import br.com.unicuritiba.pixanalyser.domain.models.PixTransaction;
import br.com.unicuritiba.pixanalyser.dto.PixKeyResponseDto;

import java.time.format.DateTimeFormatter;

public class PixKeyResponseMapper {

    public static PixKeyResponseDto toResponseDto(PixTransaction transaction, String receiverName) {
        PixKeyResponseDto response = new PixKeyResponseDto();
        response.setEndToEndId(transaction.getEndToEndId());
        response.setOriginClientId(transaction.getOriginClientId());
        response.setOriginBank(transaction.getOriginBank());
        response.setDestinationKeyValue(transaction.getDestinationKeyValue());
        response.setReceiverName(receiverName);
        response.setDestinationBank(transaction.getDestinationBank());
        response.setAmount(transaction.getAmount().toPlainString());
        response.setDescription(transaction.getDescription());
        response.setTimestamp(transaction.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return response;
    }
}
