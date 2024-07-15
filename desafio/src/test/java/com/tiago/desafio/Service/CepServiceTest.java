package com.tiago.desafio.Service;


import com.tiago.desafio.Entity.Endereco.CepResultDTO;
import com.tiago.desafio.Entity.Endereco.Endereco;
import com.tiago.desafio.Repository.EnderecoRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
class CepServiceTest {

    @InjectMocks
    private CepService cepService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    @DisplayName("Should return Cep Invalid Format")
    void cepResponseCase1() {
        String cep = "140725";
        String url = String.format("https://viacep.com.br/ws/%s/json", cep);

        String result = CepService.CepResponse(cep);
        assertThat(result).isNotNull();
        assertThat(result.matches("Cep Invalid"));
    }

    @Test
    @DisplayName("Should return Cep Non Authenticated")
    void cepResponseCase2() {
        String cep = "01001-000";
        String url = String.format("https://viacep.com.br/ws/%s/json", cep);

        when(restTemplate.getForEntity(url, CepResultDTO.class)).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        String result = CepService.CepResponse(cep);
        assertThat(result.matches("Non Authenticated"));
    }


    @Test
    @DisplayName("Should return Cep True")
    void cepResponseCase3() {
        String cep = "14407258";
        String url = String.format("https://viacep.com.br/ws/%s/json", cep);

        CepResultDTO mockResponse = new CepResultDTO();
        mockResponse.setErro(false);

        ResponseEntity<CepResultDTO> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(url, CepResultDTO.class)).thenReturn(responseEntity);

        String result = CepService.CepResponse(cep);
        assertThat(result.matches("True"));
    }

    @Test
    @DisplayName("Should return Cep False")
    void cepResponseCase4() {
        String cep = "01001-000";
        String url = String.format("https://viacep.com.br/ws/%s/json", cep);

        CepResultDTO mockResponse = new CepResultDTO();
        mockResponse.setErro(true);

        ResponseEntity<CepResultDTO> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(url, CepResultDTO.class)).thenReturn(responseEntity);

        String result = CepService.CepResponse(cep);
        assertThat(result.matches("False"));
    }

}