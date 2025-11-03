package io.github.aplaraujo.library_api_estudo.repositories;

import io.github.aplaraujo.library_api_estudo.model.Autor;
import io.github.aplaraujo.library_api_estudo.model.GeneroLivro;
import io.github.aplaraujo.library_api_estudo.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1950, 1, 12));

        var autorSalvo = autorRepository.save(autor);
        System.out.println(autorSalvo);
    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("1f4bfc6a-01d8-46d6-b4e0-9ed3a90ee719");
        Optional<Autor> autorOptional = autorRepository.findById(id);
        if (autorOptional.isPresent()) {
            Autor autorEncontrado = autorOptional.get();
            System.out.println("Dados do autor: ");
            System.out.println(autorEncontrado);
            autorEncontrado.setDataNascimento(LocalDate.of(1940, 1, 30));
            autorRepository.save(autorEncontrado);
        }

    }

    @Test
    public void listarTest() {
        List<Autor> lista = autorRepository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Contagem de autores: " + autorRepository.count());
    }

    @Test
    public void deleteByIdTest() {
        var id = UUID.fromString("b6e81df4-e05c-4fdc-9d2a-fa08d04f7153");
        autorRepository.deleteById(id);
    }

    @Test
    public void deleteTest() {
        var id = UUID.fromString("6264fa95-cc35-406b-a188-8068e40ac342");
        var maria = autorRepository.findById(id).get();
        autorRepository.delete(maria);
    }

    @Test
    void salvarAutorComLivrosTest() {
        Autor autor = new Autor();
        autor.setNome("Antonio");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1970, 12, 12));

        Livro livro1 = new Livro();
        livro1.setIsbn("0397849435");
        livro1.setPreco(BigDecimal.valueOf(100));
        livro1.setGenero(GeneroLivro.BIOGRAFIA);
        livro1.setTitulo("Minha vida");
        livro1.setDataPublicacao(LocalDate.of(1999, 12, 1));
        livro1.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("0268669279");
        livro2.setPreco(BigDecimal.valueOf(150));           // ✅ livro2
        livro2.setGenero(GeneroLivro.MISTERIO);             // ✅ livro2
        livro2.setTitulo("Assassinato no iate");            // ✅ livro2
        livro2.setDataPublicacao(LocalDate.of(2001, 12, 1)); // ✅ livro2
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro1);
        autor.getLivros().add(livro2);

        // Salva apenas o autor - os livros serão salvos automaticamente!
        autorRepository.save(autor);
    }
}
