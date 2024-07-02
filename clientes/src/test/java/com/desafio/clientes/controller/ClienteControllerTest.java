package com.desafio.clientes.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.desafio.clientes.entities.Cliente;
import com.desafio.clientes.entities.Endereco;
import com.desafio.clientes.service.ClienteService;
import com.desafio.clientes.service.TokenService;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
    private ClienteService clienteService;
    
    @Autowired
    private TokenService geradorToken;
    
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Assalto");
        endereco.setNumero("138");
        endereco.setComplemento("Apt 11");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setCep("05024000");

        cliente = new Cliente();
        cliente.setCpf("12345678900");
        cliente.setNome("João Silva");
        cliente.setData_nascimento(LocalDate.of(1955, 1, 1));
        cliente.setEmail("joao.silva@gmail.com");
        cliente.setEndereco_id(endereco);
    }
    
    @Test
    void testCreateClienteSemToken() throws Exception {
    	
    	when(clienteService.salvarCliente(any(Cliente.class))).thenReturn(cliente);
    	
    	var response = mvc.perform(
    			post("/Clientes")
    			).andReturn().getResponse();
    	
    	Assertions.assertEquals(403, response.getStatus());
    }
    
    @Test
    void testGetAllSemToken() throws Exception {
    	
    	var response = mvc.perform(
    			get("/Clientes")
    			).andReturn().getResponse();
    	
    	Assertions.assertEquals(403, response.getStatus());
    }

    @Test
    void testCreateCliente() throws Exception {
    	String token = "Bearer " + geradorToken.gerarToken("admin");
    	
    	String json = """
	        {
	            "nome": "João Silva",
	            "cpf": "12345678900",
	            "data_nascimento": "1955-01-01",
	            "email": "joao.silva@gmail.com",
	            "endereco_id": {
	                "logradouro": "Rua Assalto",
	                "numero": "138",
	                "complemento": "Apt 11",
	                "bairro": "Centro",
	                "cidade": "São Paulo",
	                "estado": "SP",
	                "cep": "05024000"
	            }
	        }
    	""";
    	
    	when(clienteService.salvarCliente(any(Cliente.class))).thenReturn(cliente);
    	
    	mvc.perform(post("/Clientes")
		 	.header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
		)
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.nome").value(cliente.getNome()))
        .andExpect(jsonPath("$.cpf").value(cliente.getCpf()))
        .andExpect(jsonPath("$.data_nascimento").value(cliente.getData_nascimento().toString()))
        .andExpect(jsonPath("$.email").value(cliente.getEmail()))
        .andExpect(jsonPath("$.endereco_id.logradouro").value(cliente.getEndereco_id().getLogradouro()))
        .andExpect(jsonPath("$.endereco_id.numero").value(cliente.getEndereco_id().getNumero()))
        .andExpect(jsonPath("$.endereco_id.complemento").value(cliente.getEndereco_id().getComplemento()))
        .andExpect(jsonPath("$.endereco_id.bairro").value(cliente.getEndereco_id().getBairro()))
        .andExpect(jsonPath("$.endereco_id.cidade").value(cliente.getEndereco_id().getCidade()))
        .andExpect(jsonPath("$.endereco_id.estado").value(cliente.getEndereco_id().getEstado()))
        .andExpect(jsonPath("$.endereco_id.cep").value(cliente.getEndereco_id().getCep()));
    }
    
    @Test
    void testGetAll() throws Exception {
        String token = "Bearer " + geradorToken.gerarToken("admin");
        
        when(clienteService.buscarTodos()).thenReturn(List.of(cliente));
        

        mvc.perform(
    		get("/Clientes")
            .header("Authorization", token)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].nome").value(cliente.getNome()))
        .andExpect(jsonPath("$[0].cpf").value(cliente.getCpf()))
        .andExpect(jsonPath("$[0].data_nascimento").value(cliente.getData_nascimento().toString()))
        .andExpect(jsonPath("$[0].email").value(cliente.getEmail()))
        .andExpect(jsonPath("$[0].endereco_id.logradouro").value(cliente.getEndereco_id().getLogradouro()))
        .andExpect(jsonPath("$[0].endereco_id.numero").value(cliente.getEndereco_id().getNumero()))
        .andExpect(jsonPath("$[0].endereco_id.complemento").value(cliente.getEndereco_id().getComplemento()))
        .andExpect(jsonPath("$[0].endereco_id.bairro").value(cliente.getEndereco_id().getBairro()))
        .andExpect(jsonPath("$[0].endereco_id.cidade").value(cliente.getEndereco_id().getCidade()))
        .andExpect(jsonPath("$[0].endereco_id.estado").value(cliente.getEndereco_id().getEstado()))
        .andExpect(jsonPath("$[0].endereco_id.cep").value(cliente.getEndereco_id().getCep()));
    }
    
    @Test
    void testGetClienteByCpf() throws Exception {
        String token = "Bearer " + geradorToken.gerarToken("admin");
        
        when(clienteService.buscarPorCpf("12345678900")).thenReturn(Optional.of(cliente));

        mvc.perform(
            get("/Clientes/12345678900")
            .header("Authorization", token)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value(cliente.getNome()))
        .andExpect(jsonPath("$.cpf").value(cliente.getCpf()))
        .andExpect(jsonPath("$.data_nascimento").value(cliente.getData_nascimento().toString()))
        .andExpect(jsonPath("$.email").value(cliente.getEmail()))
        .andExpect(jsonPath("$.endereco_id.logradouro").value(cliente.getEndereco_id().getLogradouro()))
        .andExpect(jsonPath("$.endereco_id.numero").value(cliente.getEndereco_id().getNumero()))
        .andExpect(jsonPath("$.endereco_id.complemento").value(cliente.getEndereco_id().getComplemento()))
        .andExpect(jsonPath("$.endereco_id.bairro").value(cliente.getEndereco_id().getBairro()))
        .andExpect(jsonPath("$.endereco_id.cidade").value(cliente.getEndereco_id().getCidade()))
        .andExpect(jsonPath("$.endereco_id.estado").value(cliente.getEndereco_id().getEstado()))
        .andExpect(jsonPath("$.endereco_id.cep").value(cliente.getEndereco_id().getCep()));
    }

}
