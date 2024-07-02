package com.desafio.clientes.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.clientes.entities.Cliente;
import com.desafio.clientes.entities.Endereco;
import jakarta.annotation.PostConstruct;

@Service
public class CsvService {
	
	@Autowired
    private ClienteService clienteService;

    @PostConstruct
    public void importCsv() {
    	try {
            Scanner scanner = new Scanner(new File("src/main/resources/clientes_enderecos.csv"));
           
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                Endereco endereco = new Endereco();
                endereco.setLogradouro(data[4]);
                endereco.setNumero(data[5]);
                endereco.setComplemento(data[6]);
                endereco.setBairro(data[7]);
                endereco.setCidade(data[8]);
                endereco.setEstado(data[9]);
                endereco.setCep(data[10]);

                Cliente cliente = new Cliente();
                cliente.setNome(data[0]);
                cliente.setCpf(data[1]);
                cliente.setData_nascimento(LocalDate.parse(data[2]));
                cliente.setEmail(data[3]);
                cliente.setEndereco_id(endereco);
                
                try {
                    clienteService.salvarClienteCsv(cliente);
                } catch (IllegalArgumentException e) {
                	continue;
                }
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
