package com.desafio.clientes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.desafio.clientes.entities.Cliente;
import com.desafio.clientes.entities.Endereco;
import com.desafio.clientes.repository.ClienteRepository;
import com.desafio.clientes.repository.EnderecoRepository;

class ClienteServiceTest {

	@Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private CepService cepService;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarCliente() {
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678900");
        cliente.setNome("João Silva");
        cliente.setData_nascimento(LocalDate.of(1950, 1, 1));
        cliente.setEmail("joao.silva@gmail.com");
        Endereco endereco = new Endereco();
        endereco.setCep("12345678");
        cliente.setEndereco_id(endereco);

        when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(Optional.empty());
        when(cepService.isValidCep(any(String.class))).thenReturn(true);
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente savedCliente = clienteService.salvarCliente(cliente);

        assertNotNull(savedCliente);
        assertEquals(cliente.getCpf(), savedCliente.getCpf());
        assertEquals(cliente.getNome(), savedCliente.getNome());
        assertEquals(cliente.getEmail(), savedCliente.getEmail());
        assertEquals(cliente.getEndereco_id().getCep(), savedCliente.getEndereco_id().getCep());
        assertTrue(savedCliente.getIs_idoso());
    }

    @Test
    void testBuscarPorCpf() {
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678900");
        cliente.setNome("João Silva");

        when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(Optional.of(cliente));

        Optional<Cliente> foundCliente = clienteService.buscarPorCpf(cliente.getCpf());

        assertTrue(foundCliente.isPresent());
        assertEquals(cliente.getCpf(), foundCliente.get().getCpf());
        assertEquals(cliente.getNome(), foundCliente.get().getNome());
    }

    @Test
    void testIsIdoso() {
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678900");
        cliente.setNome("João Silva");
        cliente.setData_nascimento(LocalDate.of(1955, 1, 1));
        cliente.setEmail("joao.silva@gmail.com");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente savedCliente = clienteService.salvarCliente(cliente);

        assertNotNull(savedCliente);
        assertTrue(savedCliente.getIs_idoso());
    }
    
    @Test
    void testIsNotIdoso() {
        Cliente cliente = new Cliente();
        cliente.setCpf("98765432100");
        cliente.setNome("Maria Souza");
        cliente.setData_nascimento(LocalDate.of(2023, 6, 15));
        cliente.setEmail("maria.souza@gmail.com");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente savedCliente = clienteService.salvarCliente(cliente);

        assertNotNull(savedCliente);
        assertFalse(savedCliente.getIs_idoso());
    }


}
