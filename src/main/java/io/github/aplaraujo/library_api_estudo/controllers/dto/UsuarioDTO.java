package io.github.aplaraujo.library_api_estudo.controllers.dto;

import java.util.List;

public record UsuarioDTO(String login, String senha, List<String> roles) {
}
