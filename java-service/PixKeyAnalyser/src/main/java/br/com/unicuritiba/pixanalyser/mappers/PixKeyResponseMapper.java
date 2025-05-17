package br.com.unicuritiba.pixanalyser.mappers;

import br.com.unicuritiba.pixanalyser.domain.models.PixTransaction;
import br.com.unicuritiba.pixanalyser.dto.PixKeyResponseDto;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PixKeyResponseMapper {

    public static PixKeyResponseDto toResponseDto(PixTransaction transaction, String receiverName, double confidenceScore, List<String> fraudReasons) {
        PixKeyResponseDto.Transfer transfer = new PixKeyResponseDto.Transfer();
        transfer.setEndToEndId(transaction.getEndToEndId());
        transfer.setOriginClientId(transaction.getOriginClientId());
        transfer.setOriginBank(transaction.getOriginBank());
        transfer.setDestinationKeyValue(transaction.getDestinationKeyValue());
        transfer.setReceiverName(receiverName);
        transfer.setDestinationBank(transaction.getDestinationBank());
        transfer.setAmount(transaction.getAmount().toPlainString());
        transfer.setDescription(transaction.getDescription());
        transfer.setTimestamp(transaction.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        PixKeyResponseDto.AiAnalyse analysis = new PixKeyResponseDto.AiAnalyse();
        analysis.setConfidenceScore(confidenceScore);
        analysis.setFraudReasons(fraudReasons);

        PixKeyResponseDto response = new PixKeyResponseDto();
        response.setTransfers(List.of(transfer));
        response.setAnalysis(analysis);

        return response;
    }
}
