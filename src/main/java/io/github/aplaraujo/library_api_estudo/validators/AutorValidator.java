package io.github.aplaraujo.library_api_estudo.validators;

import io.github.aplaraujo.library_api_estudo.exceptions.RegistroDuplicadoException;
import io.github.aplaraujo.library_api_estudo.model.Autor;
import io.github.aplaraujo.library_api_estudo.repositories.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {
    private AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor) {
        if (existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado!");
        }
    }

    // Verifica se existe um autor cadastrado
    private boolean existeAutorCadastrado(Autor autor) {
        // Busca de um autor no banco de dados
        Optional<Autor> autorEncontrado = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());
        // Cadastro de autores
        if (autor.getId() == null) {
            return autorEncontrado.isPresent();
        }
        // Atualização do cadastro de autores
        // Atualização: verifica se existe outro autor com os mesmos dados
        return autorEncontrado.isPresent()
                && !autor.getId().equals(autorEncontrado.get().getId());
    }
}
