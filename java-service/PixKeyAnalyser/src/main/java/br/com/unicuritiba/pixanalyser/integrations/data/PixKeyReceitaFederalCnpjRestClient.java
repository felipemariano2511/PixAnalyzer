package br.com.unicuritiba.pixanalyser.integrations.data;

import br.com.unicuritiba.pixanalyser.dto.ReceitaFederalCnpjResponseDto;
import br.com.unicuritiba.pixanalyser.dto.ReceitaFederalCnpjResponseWrapperDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PixKeyReceitaFederalCnpjRestClient {

    @Autowired
    private RestTemplate restTemplate;

    public ReceitaFederalCnpjResponseDto fetchCnpjInformation(String cnpj) {
        ResponseEntity<ReceitaFederalCnpjResponseWrapperDto> response = restTemplate.exchange(
                "https://felipemariano.com.br/api/receita-federal/cnpj/" + cnpj,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ReceitaFederalCnpjResponseWrapperDto>() {}
        );

        ReceitaFederalCnpjResponseWrapperDto wrapper = response.getBody();

        return (wrapper != null) ? wrapper.getBody() : null;
    }
}