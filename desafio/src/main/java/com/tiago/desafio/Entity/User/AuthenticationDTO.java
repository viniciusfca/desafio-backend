package com.tiago.desafio.Entity.User;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
    @NotBlank(message = "Login é obrigatório")
    String login,
    
    @NotBlank(message = "Password é obrigatório")
    String password) {
}
