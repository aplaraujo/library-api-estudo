package io.github.aplaraujo.library_api_estudo.controllers.dto;

import io.github.aplaraujo.library_api_estudo.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

// Data Transfer Object - Objeto de transferência de dados
// Classe imutável para recebimento dos dados e criar uma entidade
// Representação do contrato da API
public record AutorDTO(
        UUID id,

        @NotBlank(message = "Campo obrigatório") // A informação não pode ser nula nem vazia
        String nome,

        @NotNull(message = "Campo obrigatório") // A informação não pode ser nula
        LocalDate dataNascimento,

        @NotBlank(message = "Campo obrigatório")
        String nacionalidade) {

    public Autor mapearParaAutor() {
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
