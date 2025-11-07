package io.github.aplaraujo.library_api_estudo.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

// Data Transfer Object - Objeto de transferência de dados
// Classe imutável para recebimento dos dados e criar uma entidade
// Representação do contrato da API
@Schema(name = "Autor")
public record AutorDTO(
        UUID id,

        @NotBlank(message = "Campo obrigatório") // A informação não pode ser nula nem vazia
        @Size(min = 2, max = 100, message = "Campo fora do tamanho padrão") // Determinar o tamanho máximo de uma string
        @Schema(name = "nome")
        String nome,

        @NotNull(message = "Campo obrigatório") // A informação não pode ser nula
        @Past(message = "Não pode ser uma data futura") // Aceita data passada
        @Schema(name = "dataNascimento")
        LocalDate dataNascimento,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 3, max = 50, message = "Campo fora do tamanho padrão")
        @Schema(name = "nacionalidade")
        String nacionalidade) {

}
