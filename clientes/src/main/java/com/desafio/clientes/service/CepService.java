package com.desafio.clientes.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CepService {

    private final RestTemplate restTemplate;

    public CepService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
 
    public boolean isValidCep(String cep) {
        String url = String.format("https://viacep.com.br/ws/%s/json/", cep);
        try {
            String response = restTemplate.getForObject(url, String.class);
            return response != null && response.contains("\"cep\":");
        } catch (Exception e) {
            return false;
        }
    }
}
