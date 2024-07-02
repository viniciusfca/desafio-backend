package com.desafio.clientes.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.clientes.entities.Usuario;
import com.desafio.clientes.service.TokenService;

@RestController
@RequestMapping("/Login")
public class UsuarioController {
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<Object> autenticacao(@RequestBody Usuario usuario) {
		var user = new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getPassword());
		manager.authenticate(user);
		
		var token = tokenService.gerarToken(usuario.getLogin());

		return ResponseEntity.ok(Map.of("token", token));
	}
}
