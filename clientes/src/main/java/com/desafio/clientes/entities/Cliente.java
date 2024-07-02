package com.desafio.clientes.entities;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {

	@Id
    @GeneratedValue
    private UUID id;

	@Column(unique = true)
	private String cpf;
	
    private String nome;
    private LocalDate data_nascimento;
    private String email;
    private Boolean is_idoso;
    
    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco_id;
    
    
    public Endereco getEndereco_id() {
		return endereco_id;
	}

	public void setEndereco_id(Endereco endereco_id) {
		this.endereco_id = endereco_id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(LocalDate data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public Boolean getIs_idoso() {
		return is_idoso;
	}

	public void setIs_idoso(Boolean is_idoso) {
		this.is_idoso = is_idoso;
	}
}
