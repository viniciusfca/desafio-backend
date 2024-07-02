package com.desafio.clientes.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.desafio.clientes.repository.UsuarioRepository;
import com.desafio.clientes.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			
			var tokenJWT = getToken(request);
			String subject;
			
			if (tokenJWT != null) {
				try {
					subject = tokenService.getSubject(tokenJWT);
				} catch (RuntimeException e) {
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
					response.setContentType("application/json");
					response.getWriter().write("{\"error\":\"Token JWT inválido ou expirado\"}");
					return;
				}
				
				var usuario = repository.findByLogin(subject);
		        var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

		        SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
			filterChain.doFilter(request, response);
	}
	
	private String getToken(HttpServletRequest request) {
	    var authorizationHeader = request.getHeader("Authorization");
	    if (authorizationHeader == null) {
	    	return null;
	    }

	    return authorizationHeader.replace("Bearer ", "");
	}

}
