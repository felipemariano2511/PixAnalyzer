package br.com.unicuritiba.pixanalyzer.integrations.data;

import br.com.unicuritiba.pixanalyzer.domain.dto.DadosGovResponseDto;
import br.com.unicuritiba.pixanalyzer.domain.dto.DadosGovResponseWrapperDto;
import br.com.unicuritiba.pixanalyzer.infrastructure.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class PixKeyDadosGovRestClient {
    
    @Autowired
    private RestTemplate restTemplate;

    public DadosGovResponseDto fetchCpfInformation(String cpf) {

        try {
            ResponseEntity<DadosGovResponseWrapperDto> response = restTemplate.exchange(
                    "https://felipemariano.com.br/api/dados-gov/cpf/" + cpf,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<DadosGovResponseWrapperDto>() {
                    }
            );

            return response.getBody().getBody();

        } catch (
                HttpClientErrorException.NotFound e) {
            throw new NotFoundException(String.format(
                    "CPF: %s não encontrada na base de dados do DadosGov.", cpf));
        }
    }
}
