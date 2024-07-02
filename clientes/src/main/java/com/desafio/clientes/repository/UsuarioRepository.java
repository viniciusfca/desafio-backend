package com.desafio.clientes.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.desafio.clientes.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

	UserDetails findByLogin(String login);

}
