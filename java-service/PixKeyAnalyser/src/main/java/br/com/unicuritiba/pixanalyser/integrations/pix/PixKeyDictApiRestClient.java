package br.com.unicuritiba.pixanalyser.integrations.pix;

import br.com.unicuritiba.pixanalyser.dto.DictApiResponseDto;
import br.com.unicuritiba.pixanalyser.dto.DictApiResponseWrapperDto;
import br.com.unicuritiba.pixanalyser.infrastructure.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Component
public class PixKeyDictApiRestClient {

    @Autowired
    private RestTemplate restTemplate;

    public DictApiResponseDto fetchKeyInformation(String destinationKey) {
        try {
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("key", destinationKey);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<DictApiResponseWrapperDto> response = restTemplate.exchange(
                    "https://felipemariano.com.br/api/dict-api/keys",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<DictApiResponseWrapperDto>() {}
            );

            return response.getBody().getBody();

        } catch (HttpClientErrorException e) {
            throw new NotFoundException(String.format(
                    "Chave Pix: %s não encontrada na base de dados do DICT.", destinationKey));
        }
    }
}
