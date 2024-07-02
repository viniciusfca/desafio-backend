package com.desafio.clientes.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class CepServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CepService cepService;

    public CepServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidCep() {
        String validCep = "01001000";
        String mockResponse = "{\"cep\":\"01001-000\",\"logradouro\":\"Praça da Sé\",\"complemento\":\"lado ímpar\"," +
                "\"bairro\":\"Sé\",\"localidade\":\"São Paulo\",\"uf\":\"SP\",\"ibge\":\"3550308\",\"gia\":\"1004\"," +
                "\"ddd\":\"11\",\"siafi\":\"7107\"}";

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(mockResponse);


        assertTrue(cepService.isValidCep(validCep));
    }

    @Test
    void testInvalidCep() {
        String invalidCep = "00000000";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(null);

        assertFalse(cepService.isValidCep(invalidCep));
    }

}