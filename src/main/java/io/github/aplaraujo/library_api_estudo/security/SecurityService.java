package io.github.aplaraujo.library_api_estudo.security;

import io.github.aplaraujo.library_api_estudo.model.Usuario;
import io.github.aplaraujo.library_api_estudo.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioService usuarioService;

    public Usuario obterUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails details = (UserDetails) authentication.getPrincipal();
        String login = details.getUsername();
        return usuarioService.obterPorLogin(login);
    }
}
