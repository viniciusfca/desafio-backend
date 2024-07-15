package com.tiago.desafio.Service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.tiago.desafio.Entity.Cliente;
import com.tiago.desafio.Entity.Endereco.Endereco;
import com.tiago.desafio.Repository.ClienteRepository;
import com.tiago.desafio.Repository.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.anyString;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CSVServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private CepService cepService;

    @InjectMocks
    private CSVService csvService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void lerCSV() throws IOException, CsvValidationException {
        String fileReader= "src/test/resources/data/clientes_enderecos-test.csv";

        csvService.lerCSV(fileReader);

        //when(CepService.CepResponse(anyString()).replaceAll(anyString(),"True"));
        //when(CepService.CepResponse(any())).thenReturn("True");



        verify(clienteRepository, times(2)).save(any(Cliente.class));
        verify(enderecoRepository, times(2)).save(any(Endereco.class));
    }
}