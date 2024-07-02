package com.desafio.clientes.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {
	
	@Value("{api.token.secret}")
	private String secret;
	
	private String api = "API Cliente";
	
	public String gerarToken(String usuario) { 
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                .withIssuer(api)
                .withSubject(usuario)
                .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
                .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerrar token jwt", exception);
        }		
    }
	
	public String getSubject(String tokenJWT) {
	    try {
	        var algoritmo = Algorithm.HMAC256(secret);
	        return JWT.require(algoritmo)
	                        .withIssuer(api)
	                        .build()
	                        .verify(tokenJWT)
	                        .getSubject();
	    } catch (JWTVerificationException exception) {
	        throw new RuntimeException("Token JWT inválido ou expirado!");
	    }
	}
}
