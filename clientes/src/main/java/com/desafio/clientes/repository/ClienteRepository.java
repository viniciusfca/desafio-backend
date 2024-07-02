package com.desafio.clientes.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.clientes.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID>{
	Optional<Cliente> findByCpf(String cpf);
}
