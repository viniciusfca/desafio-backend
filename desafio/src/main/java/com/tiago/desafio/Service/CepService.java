package com.tiago.desafio.Service;

import com.tiago.desafio.Entity.Endereco.CepResultDTO;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CepService {
    private static String viacepUrl = "https://viacep.com.br/ws/%s/json";
    private static String postmonUrl = "https://api.postmon.com.br/v1/cep/%s";
    private static boolean usarOutraApi = false;

    private static boolean isCepValidFormat(String cep) {
        // Verifica se o CEP tem o formato XXXXX-XXX ou XXXXXXXX
        return cep.matches("\\d{5}-\\d{3}") || cep.matches("\\d{8}");
    }

    public static String CepResponse(String cep) {
        String cepUrl = usarOutraApi ? String.format(postmonUrl, cep) : String.format(viacepUrl, cep);
        RestTemplate restTemplate = new RestTemplate();

        try {
            if (!isCepValidFormat(cep)) {
                return "Cep Invalid";
            }

            ResponseEntity<CepResultDTO> responseEntity = restTemplate.getForEntity(cepUrl, CepResultDTO.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                CepResultDTO response = responseEntity.getBody();
                if (response != null) {
                    return response.isErro() ? "False" : "True" ;
                } else {
                    throw new RuntimeException("Response body is null");
                }
            } else {
                throw new RuntimeException("HTTP Status code: " + responseEntity.getStatusCodeValue());
            }
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Trata erros de cliente e servidor (4xx e 5xx)
            ex.printStackTrace();

            if (ex.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE){
                return "False";
            }

            // Se ocorrer erro ao acessar ViaCEP, tenta usar a API alternativa
            if (!usarOutraApi) {
                usarOutraApi = true;
                return CepResponse(cep); // Tenta novamente com a API alternativa
            }

            return "Non Authenticated";
        } catch (Exception e) {
            // Trata qualquer outra exceção
            e.printStackTrace();

            // Se ocorrer erro ao acessar ViaCEP, tenta usar a API alternativa
            if (!usarOutraApi) {
                usarOutraApi = true;
                return CepResponse(cep); // Tenta novamente com a API alternativa
            }

            return "Non Authenticated";
        }
    }
}
