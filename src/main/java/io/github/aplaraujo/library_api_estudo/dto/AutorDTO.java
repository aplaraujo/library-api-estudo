package io.github.aplaraujo.library_api_estudo.dto;

import java.time.LocalDate;

// Data Transfer Object - Objeto de transferência de dados
// Classe imutável para recebimento dos dados e criar uma entidade
// Representação do contrato da API
public record AutorDTO(String nome, LocalDate dataNascimento, String nacionalidade) {
}
