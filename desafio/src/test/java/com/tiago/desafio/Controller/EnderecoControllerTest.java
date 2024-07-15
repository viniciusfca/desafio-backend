package com.tiago.desafio.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiago.desafio.Entity.Endereco.Endereco;
import com.tiago.desafio.Repository.EnderecoRepository;
import com.tiago.desafio.Service.EnderecoService;
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
class EnderecoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EnderecoService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private EnderecoRepository repository;

    @Autowired
    @InjectMocks
    private EnderecoController enderecoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "user1", roles = {"ADMIN"})
    @DisplayName("Should create endereco")
    void testCreateEndereco() throws Exception {
        // Mock do objeto endereco que você deseja criar
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Lapada");
        endereco.setNumero("12345");
        endereco.setComplemento("ap1");
        endereco.setBairro("Moro");
        endereco.setCidade("Franca");
        endereco.setEstado("SP");
        endereco.setCep("14407258");

        // Mock do retorno do serviço
        when(service.create(any(Endereco.class))).thenReturn(endereco);



        // Perform POST request
        mockMvc.perform(post("/enderecos")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()) // Adiciona token CSRF se necessário
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user1", "password")) // Mock da autenticação básica
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endereco)))
                .andExpect(status().isCreated());

        // Verifique se o serviço foi chamado corretamente
        verify(service, times(1)).create(any(Endereco.class));
    }


    @Test
    @WithMockUser(username = "user1", roles = {"ADMIN"})
    @DisplayName("Should get all endereco")
    void testGetAll() throws Exception {
        // Mock do objeto endereco que você deseja criar
        Endereco endereco1 = new Endereco();
        endereco1.setLogradouro("Lapada");
        endereco1.setNumero("12345");
        endereco1.setComplemento("ap1");
        endereco1.setBairro("Moro");
        endereco1.setCidade("Franca");
        endereco1.setEstado("SP");
        endereco1.setCep("14407258");

        // Perform POST request
        mockMvc.perform(post("/enderecos")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()) // Adiciona token CSRF se necessário
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user1", "password")) // Mock da autenticação básica
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endereco1)))
                .andExpect(status().isCreated());

        //endereco2
        // Mock do objeto endereco que você deseja criar
        Endereco endereco2 = new Endereco();
        endereco2.setLogradouro("Lapada-2");
        endereco2.setNumero("12345-2");
        endereco2.setComplemento("ap1-2");
        endereco2.setBairro("Moro-2");
        endereco2.setCidade("Franca-2");
        endereco2.setEstado("SP-2");
        endereco2.setCep("14407258-2");
        // Perform POST request
        mockMvc.perform(post("/enderecos")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()) // Adiciona token CSRF se necessário
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user1", "password")) // Mock da autenticação básica
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endereco2)))
                .andExpect(status().isCreated());

        // Perform GET
        mockMvc.perform(get("/enderecos")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()) // Adiciona token CSRF se necessário
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user1", "password")) // Mock da autenticação básica
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk());



        List<Endereco> expectedenderecos = Arrays.asList(endereco1, endereco2);
        when(service.getAll()).thenReturn(expectedenderecos);

        List<Endereco> result = service.getAll();

        verify(service, times(2)).getAll();
        assertThat(result).containsAll(expectedenderecos);

    }
}