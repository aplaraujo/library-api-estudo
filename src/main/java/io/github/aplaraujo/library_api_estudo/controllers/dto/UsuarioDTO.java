package io.github.aplaraujo.library_api_estudo.controllers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UsuarioDTO(
        @NotNull(message = "Campo obrigatório")
        String login,
        @NotNull(message = "Campo obrigatório")
        String senha,
        @Email(message = "Email inválido!")
        @NotNull(message = "Campo obrigatório")
        String email,
        List<String> roles) {
}
