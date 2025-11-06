package io.github.aplaraujo.library_api_estudo.validators;

import io.github.aplaraujo.library_api_estudo.exceptions.CampoInvalidoException;
import io.github.aplaraujo.library_api_estudo.exceptions.RegistroDuplicadoException;
import io.github.aplaraujo.library_api_estudo.model.Livro;
import io.github.aplaraujo.library_api_estudo.repositories.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {
    private final LivroRepository livroRepository;

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    public void validar(Livro livro) {
        if (existeLivroComIsbn(livro)) {
            throw new RegistroDuplicadoException("ISBN já cadastrado!");
        }

        if (isPrecoObrigatorioNulo(livro)) {
            throw new CampoInvalidoException("preco", "Para livros com ano de publicação a partir de 2020, o preço é obrigatorio!");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    // Verificar se existe livro com ISBN (cadastro ou atualização)
    private boolean existeLivroComIsbn(Livro livro) {
        Optional<Livro> optionalLivro = livroRepository.findByIsbn(livro.getIsbn());
        if (livro.getId() == null) {
            return optionalLivro.isPresent();
        }
        return optionalLivro.map(Livro::getId).stream().anyMatch(id -> !id.equals(livro.getId()));
    }
}
