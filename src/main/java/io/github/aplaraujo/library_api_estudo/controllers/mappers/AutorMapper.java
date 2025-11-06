package io.github.aplaraujo.library_api_estudo.controllers.mappers;

import io.github.aplaraujo.library_api_estudo.controllers.dto.AutorDTO;
import io.github.aplaraujo.library_api_estudo.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // Transforma em um componente que pode ser injetado
public interface AutorMapper {
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "dataNascimento", target = "dataNascimento")
    @Mapping(source = "nacionalidade", target = "nacionalidade")
    Autor toEntity(AutorDTO dto); // Transforma DTO em entidade

    AutorDTO toDTO(Autor autor); // Transforma entidade em DTO
}
