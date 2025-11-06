package io.github.aplaraujo.library_api_estudo.security;

import io.github.aplaraujo.library_api_estudo.model.Usuario;
import io.github.aplaraujo.library_api_estudo.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioService service;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = service.obterPorLogin(login);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(usuario.getRoles().toArray(new String[usuario.getRoles().size()]))
                .build();
    }
}
