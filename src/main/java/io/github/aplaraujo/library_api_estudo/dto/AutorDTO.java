package io.github.aplaraujo.library_api_estudo.dto;

import io.github.aplaraujo.library_api_estudo.model.Autor;

import java.time.LocalDate;

// Data Transfer Object - Objeto de transferência de dados
// Classe imutável para recebimento dos dados e criar uma entidade
// Representação do contrato da API
public record AutorDTO(String nome, LocalDate dataNascimento, String nacionalidade) {

    public Autor mapearParaAutor() {
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
