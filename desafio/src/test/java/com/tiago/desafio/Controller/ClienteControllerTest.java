package com.tiago.desafio.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiago.desafio.Entity.Cliente;
import com.tiago.desafio.Repository.ClienteRepository;
import com.tiago.desafio.Service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ClienteService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private ClienteRepository repository;

    @Autowired
    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "user1", roles = {"ADMIN"})
    @DisplayName("Should create Cliente")
    void testCreateCliente() throws Exception {
        // Mock do objeto Cliente que você deseja criar
        Cliente cliente = new Cliente();
        cliente.setNome("John Doe");
        cliente.setCpf("123.456.789-00");
        cliente.setDataNascimento(LocalDate.of(1980,1,1));
        cliente.setEmail("john.doe@example.com");

        // Mock do retorno do serviço
        when(service.create(any(Cliente.class))).thenReturn(cliente);



        // Perform POST request
        mockMvc.perform(post("/clientes")
                .with(SecurityMockMvcRequestPostProcessors.csrf()) // Adiciona token CSRF se necessário
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("user1", "password")) // Mock da autenticação básica
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated());

        // Verifique se o serviço foi chamado corretamente
        verify(service, times(1)).create(any(Cliente.class));
    }


    @Test
    @WithMockUser(username = "user1", roles = {"ADMIN"})
    @DisplayName("Should get a Cliente")
    void testGetCpf() throws Exception {
        // Mock do objeto Cliente que você deseja criar
        Cliente cliente = new Cliente();
        cliente.setNome("John Doe");
        cliente.setCpf("123.456.789-00");
        cliente.setDataNascimento(LocalDate.of(1980,1,1));
        cliente.setEmail("john.doe@example.com");

        // Mock do retorno do serviço
        when(service.create(any(Cliente.class))).thenReturn(any(Cliente.class));
        when(service.getCpf(cliente.getCpf())).thenReturn(cliente);

        // Perform POST request
        mockMvc.perform(post("/clientes")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()) // Adiciona token CSRF se necessário
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user1", "password")) // Mock da autenticação básica
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated());


        // Perform GET
        mockMvc.perform(get("/clientes/"+ cliente.getCpf())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user1", "password")) // Mock da autenticação básica
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk());

        // Verifique se o serviço foi chamado corretamente
        verify(service, times(1)).create(any(Cliente.class));


        verify(service, times(1)).getCpf(cliente.getCpf());
        assertThat(cliente.getCpf()).isEqualTo("123.456.789-00");
    }

    @Test
    @WithMockUser(username = "user1", roles = {"ADMIN"})
    @DisplayName("Should get all Cliente")
    void testGetAll() throws Exception {
        // Mock do objeto Cliente que você deseja criar
        Cliente cliente1 = new Cliente();
        cliente1.setNome("John Doe");
        cliente1.setCpf("123.456.789-00");
        cliente1.setDataNascimento(LocalDate.of(1980,1,1));
        cliente1.setEmail("john.doe@example.com");

        // Perform POST request
        mockMvc.perform(post("/clientes")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()) // Adiciona token CSRF se necessário
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user1", "password")) // Mock da autenticação básica
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente1)))
                .andExpect(status().isCreated());

        //cliente2
        // Mock do objeto Cliente que você deseja criar
        Cliente cliente2 = new Cliente();
        cliente2.setNome("novo");
        cliente2.setCpf("novo");
        cliente2.setDataNascimento(LocalDate.of(1980,1,1));
        cliente2.setEmail("novo");

        // Perform POST request
        mockMvc.perform(post("/clientes")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()) // Adiciona token CSRF se necessário
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user1", "password")) // Mock da autenticação básica
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente2)))
                        .andExpect(status().isCreated());

        // Perform GET
        mockMvc.perform(get("/clientes")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()) // Adiciona token CSRF se necessário
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user1", "password")) // Mock da autenticação básica
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                        .andExpect(status().isOk());



        List<Cliente> expectedClientes = Arrays.asList(cliente1, cliente2);
        when(service.getAll()).thenReturn(expectedClientes);

        List<Cliente> result = service.getAll();

        verify(service, times(2)).getAll();
        assertThat(result).containsAll(expectedClientes);

    }
}