package com.desafio.clientes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.desafio.clientes.entities.Usuario;
import com.desafio.clientes.repository.UsuarioRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return repository.findByLogin(username);
	}

	@PostConstruct
    public void CadastroUsuario() {
		Usuario usuario = new Usuario();
		usuario.setLogin("admin");
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    usuario.setPassword(passwordEncoder.encode("admin"));
	    
		usuario = repository.save(usuario);
    }
}
