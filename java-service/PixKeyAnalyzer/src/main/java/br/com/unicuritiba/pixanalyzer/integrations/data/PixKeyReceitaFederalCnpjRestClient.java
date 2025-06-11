package br.com.unicuritiba.pixanalyzer.integrations.data;

import br.com.unicuritiba.pixanalyzer.domain.dto.ReceitaFederalCnpjResponseDto;
import br.com.unicuritiba.pixanalyzer.domain.dto.ReceitaFederalCnpjResponseWrapperDto;
import br.com.unicuritiba.pixanalyzer.infrastructure.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class PixKeyReceitaFederalCnpjRestClient {

    @Autowired
    private RestTemplate restTemplate;

    public ReceitaFederalCnpjResponseDto fetchCnpjInformation(String cnpj) {

        try {
            ResponseEntity<ReceitaFederalCnpjResponseWrapperDto> response = restTemplate.exchange(
                    "https://felipemariano.com.br/api/receita-federal/cnpj/" + cnpj,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ReceitaFederalCnpjResponseWrapperDto>() {}
            );

            ReceitaFederalCnpjResponseWrapperDto wrapper = response.getBody();

            return (wrapper != null) ? wrapper.getBody() : null;
        } catch (
                HttpClientErrorException.NotFound e) {
            throw new NotFoundException(String.format(
                    "CNPJ: %s não encontrada na base de dados da Receita Federal.", cnpj));
        }
    }
}