package io.github.aplaraujo.library_api_estudo.repositories;

import io.github.aplaraujo.library_api_estudo.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

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
}
