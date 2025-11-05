package io.github.aplaraujo.library_api_estudo.services;

import io.github.aplaraujo.library_api_estudo.model.GeneroLivro;
import io.github.aplaraujo.library_api_estudo.model.Livro;
import io.github.aplaraujo.library_api_estudo.repositories.LivroRepository;
import static io.github.aplaraujo.library_api_estudo.repositories.specs.LivroSpecs.*;

import io.github.aplaraujo.library_api_estudo.validators.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;

    public Livro salvar(Livro livro) {
        livroValidator.validar(livro);
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return livroRepository.findWithAutorById(id);
    }

    public void excluir(Livro livro) {
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisa(String isbn, String nomeAutor, String titulo, GeneroLivro genero, Integer anoPublicacao) {
        // select * from livro where 0 = 0
        Specification<Livro> specification = Specification
                .where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        if (isbn != null) {
            // query = query and isbn = :isbn
            specification = specification.and(isbnEqual(isbn));
        }

        if (titulo != null) {
            specification = specification.and(tituloLike(titulo));
        }

        if (genero != null) {
            specification = specification.and(generoEqual(genero));
        }

        if (anoPublicacao != null) {
            specification = specification.and(anoPublicacaoEqual(anoPublicacao));
        }

        if (nomeAutor != null) {
            specification = specification.and(nomeAutorLike(nomeAutor));
        }

        return livroRepository.findAll();
    }

    public void atualizar(Livro livro) {
        if (livro.getId() == null) {
            throw new IllegalArgumentException("Livro não encontrado!");
        }
        livroValidator.validar(livro);
        livroRepository.save(livro);
    }
}
