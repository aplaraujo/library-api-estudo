package io.github.aplaraujo.library_api_estudo.repositories;

import io.github.aplaraujo.library_api_estudo.model.Autor;
import io.github.aplaraujo.library_api_estudo.model.GeneroLivro;
import io.github.aplaraujo.library_api_estudo.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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

    @Test
    @Transactional // Abriu uma janela para operar transaçoes com o banco de dados
    void buscarLivroTest() {
        UUID id = UUID.fromString("d669f26f-c368-42eb-aab0-37b9e735694f");
        Livro livro = livroRepository.findById(id).orElse(null);
        System.out.println("Nome do livro: ");
        System.out.println(livro.getTitulo());

        System.out.println("Nome do autor: ");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest() {
        List<Livro> lista = livroRepository.findByTitulo("Minha vida");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbnTest() {
        List<Livro> lista = livroRepository.findByIsbn("0268669279");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPrecoTest() {
        var preco = BigDecimal.valueOf(100);
        var titulo = "Minha vida";
        List<Livro> lista = livroRepository.findByTituloAndPreco(titulo, preco);
        lista.forEach(System.out::println);
    }

    @Test
    void listaLivrosComQueryJPQL() {
        var resultado = livroRepository.listaLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listaAutoresDosLivros() {
        var resultado = livroRepository.listaAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listaTitulosDosLivros() {
        var resultado = livroRepository.listaNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listaGenerosDosLivros() {
        var resultado = livroRepository.listaGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listaPorGeneroQueryParamTest() {
        // Usando o método com Sort dinâmico
        var resultado = livroRepository.findByGenero(
                GeneroLivro.BIOGRAFIA,
                Sort.by("dataPublicacao")
        );
        resultado.forEach(System.out::println);
    }

    @Test
    void listaPorGeneroComPrecoTest() {
        // Ordenando por preço descendente
        var resultado = livroRepository.findByGenero(
                GeneroLivro.BIOGRAFIA,
                Sort.by("preco").descending()
        );
        resultado.forEach(System.out::println);
    }

    @Test
    void listaPorGeneroOrdenacaoFixaTest() {
        // Se criou o método com ordenação fixa
        var resultado = livroRepository.findByGeneroOrdenadoPorData(GeneroLivro.BIOGRAFIA);
        resultado.forEach(System.out::println);
    }

    @Test
    void deletePorGeneroTest() {
        livroRepository.deleteByGenero(GeneroLivro.BIOGRAFIA);
    }

    @Test
    void updateDataPublicacaoTest() {
        livroRepository.updateDataPublicacao(LocalDate.of(2010, 12, 1));
    }
}