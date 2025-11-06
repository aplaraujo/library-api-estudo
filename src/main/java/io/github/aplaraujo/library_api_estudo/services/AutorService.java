package io.github.aplaraujo.library_api_estudo.services;

import io.github.aplaraujo.library_api_estudo.exceptions.OperacaoNaoPermitidaException;
import io.github.aplaraujo.library_api_estudo.model.Autor;
import io.github.aplaraujo.library_api_estudo.model.Usuario;
import io.github.aplaraujo.library_api_estudo.repositories.AutorRepository;
import io.github.aplaraujo.library_api_estudo.repositories.LivroRepository;
import io.github.aplaraujo.library_api_estudo.security.SecurityService;
import io.github.aplaraujo.library_api_estudo.validators.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor // Cria um construtor com campos obrigatórios
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;

    public Autor salvar(Autor autor) {
        autorValidator.validar(autor);
        Usuario usuario = securityService.obterUsuarioAutenticado();
        autor.setUsuario(usuario);
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return autorRepository.findById(id);
    }

    public void excluir(Autor autor) {
        if (possuiLivro(autor)) {
            throw new OperacaoNaoPermitidaException("Esse autor possui livros cadastrados!");
        }
        autorRepository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null) {
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if (nome != null) {
            return autorRepository.findByNome(nome);
        }

        if (nacionalidade != null) {
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findAll();
    }


    public void atualizar(Autor autor) {
        if (autor.getId() == null) {
            throw new IllegalArgumentException("Autor não encontrado!");
        }
        autorValidator.validar(autor);
        autorRepository.save(autor);
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade) {
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id", "dataNascimento", "dataCadastro").withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);
        return autorRepository.findAll(autorExample);
    }

    // Verificar se o autor possui algum livro publicado
    public boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }
}
