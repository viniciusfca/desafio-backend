package com.tiago.desafio.Repository;

import com.tiago.desafio.Entity.Endereco.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
}
