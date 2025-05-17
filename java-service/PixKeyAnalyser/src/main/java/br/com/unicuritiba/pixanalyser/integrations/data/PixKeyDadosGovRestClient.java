package br.com.unicuritiba.pixanalyser.integrations.data;

import br.com.unicuritiba.pixanalyser.dto.DadosGovResponseDto;
import br.com.unicuritiba.pixanalyser.dto.DadosGovResponseWrapperDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PixKeyDadosGovRestClient {
    
    @Autowired
    private RestTemplate restTemplate;

    public DadosGovResponseDto fetchCpfInformation(String cpf) {

        ResponseEntity<DadosGovResponseWrapperDto> response = restTemplate.exchange(
                "https://felipemariano.com.br/api/dados-gov/cpf/" + cpf,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DadosGovResponseWrapperDto>() {}
        );

        return response.getBody().getBody();
    }

    
    
}
