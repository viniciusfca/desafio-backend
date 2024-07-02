package com.desafio.clientes.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.clientes.entities.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {

}
