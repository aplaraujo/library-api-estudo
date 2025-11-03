package io.github.aplaraujo.library_api_estudo.repositories;

import io.github.aplaraujo.library_api_estudo.model.Autor;
import io.github.aplaraujo.library_api_estudo.model.GeneroLivro;
import io.github.aplaraujo.library_api_estudo.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

//    @Test
//    void salvarTest() {
//        Livro livro = new Livro();
//        livro.setIsbn("9781234567897");
//        livro.setPreco(BigDecimal.valueOf(100));
//        livro.setGenero(GeneroLivro.FICCAO);
//        livro.setTitulo("Eu vi um ET");
//        livro.setDataPublicacao(LocalDate.of(1990, 1, 1));
//
//        Autor autor = autorRepository.findById(UUID.fromString("377d69c4-b50a-4bb6-b146-b81bcd6ac8a4")).orElse(null);
//        livro.setAutor(autor);
//
//        livroRepository.save(livro);
//    }

    // Operação em cascata: não é recomendada para projetos em produção!
    @Test
    void saveCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("0463112245");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Eu vi um ET parte 2");
        livro.setDataPublicacao(LocalDate.of(1991, 1, 1));

        Autor autor = new Autor();
        autor.setNome("Helena");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1970, 1, 12));

        livro.setAutor(autor);
        livroRepository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro() {
        UUID id = UUID.fromString("d669f26f-c368-42eb-aab0-37b9e735694f");
        var livroParaAtualizar = livroRepository.findById(id).orElse(null);
        UUID autorId = UUID.fromString("377d69c4-b50a-4bb6-b146-b81bcd6ac8a4");
        Autor autor = autorRepository.findById(autorId).orElse(null);

        livroParaAtualizar.setAutor(autor);
        livroRepository.save(livroParaAtualizar);
    }

    @Test
    void deleteTest() {
        UUID id = UUID.fromString("9f3379fd-d321-4887-83ca-695265275b3f");
        livroRepository.deleteById(id);
    }
}