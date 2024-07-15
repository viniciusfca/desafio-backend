package com.tiago.desafio.Entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
    @NotBlank(message = "Login é obrigatório")
    String login,

    @NotBlank(message = "Password é obrigatório")
    String password, 

    @NotNull(message = "Role precisa ser ADMIN ou USER")
    UserRole role) {
}
