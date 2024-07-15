package com.tiago.desafio.Service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.tiago.desafio.Entity.Cliente;
import com.tiago.desafio.Entity.Endereco.Endereco;
import com.tiago.desafio.Repository.ClienteRepository;
import com.tiago.desafio.Repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class CSVService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;





    public void lerCSV(String filep) throws IOException, CsvValidationException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try (CSVReader reader = new CSVReader(new FileReader(filep))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                Cliente cliente = new Cliente();
                cliente.setNome(line[0]);
                cliente.setCpf(line[1]);
                cliente.setDataNascimento(LocalDate.parse(line[2], formatter));
                cliente.setEmail(line[3]);
                clienteRepository.save(cliente);

                Endereco endereco = new Endereco();
                endereco.setLogradouro(line[4]);
                endereco.setNumero(line[5]);
                endereco.setComplemento(line[6]);
                endereco.setBairro(line[7]);
                endereco.setCidade(line[8]);
                endereco.setEstado(line[9]);
                endereco.setCep(line[10]);
                endereco.setClienteId(cliente);

                String cepValidationResult = CepService.CepResponse(endereco.getCep());
                endereco.setCepResponse(cepValidationResult);

                enderecoRepository.save(endereco);
            }
        }
    }
}