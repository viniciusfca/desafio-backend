package com.desafio.clientes.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.desafio.clientes.entities.Cliente;
import com.desafio.clientes.entities.Endereco;
import com.desafio.clientes.repository.ClienteRepository;
import com.desafio.clientes.repository.EnderecoRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
    private CepService cepService;
	
	public Cliente salvarCliente(Cliente cliente) {
		
        if (repository.findByCpf(cliente.getCpf()).isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado: " + cliente.getCpf());
        }
		
		Endereco endereco = cliente.getEndereco_id();
		
		if (endereco != null) {
            if (!cepService.isValidCep(endereco.getCep())) {
                throw new IllegalArgumentException("CEP inválido");
            }
            endereco = enderecoRepository.save(endereco);
            cliente.setEndereco_id(endereco);
        }
		
        cliente.setIs_idoso(calcularIdade(cliente.getData_nascimento()) > 60);

        return repository.save(cliente);
    }
	
	public Cliente salvarClienteCsv(Cliente cliente) {
		
		if (cliente.getEndereco_id() != null) {
		    Endereco endereco = enderecoRepository.save(cliente.getEndereco_id());
		    cliente.setEndereco_id(endereco);
		}
		
        cliente.setIs_idoso(calcularIdade(cliente.getData_nascimento()) > 60);

        try {
            return repository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("CPF já cadastrado: " + cliente.getCpf());
        }
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    public List<Cliente> buscarTodos() {
        return repository.findAll();
    }

    private int calcularIdade(LocalDate data_nascimento) {
        if (data_nascimento == null) {
            return 0;
        }
        return Period.between(data_nascimento, LocalDate.now()).getYears();
    }
}


