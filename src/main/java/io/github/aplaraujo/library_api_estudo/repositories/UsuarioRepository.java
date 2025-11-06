package io.github.aplaraujo.library_api_estudo.repositories;

import io.github.aplaraujo.library_api_estudo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Usuario findByLogin(String login);

    Usuario findByEmail(String email);
}
