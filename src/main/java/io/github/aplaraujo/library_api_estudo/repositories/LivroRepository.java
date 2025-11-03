package io.github.aplaraujo.library_api_estudo.repositories;

import io.github.aplaraujo.library_api_estudo.model.Autor;
import io.github.aplaraujo.library_api_estudo.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
    // Método de consulta
    List<Livro> findByAutor(Autor autor);
}
