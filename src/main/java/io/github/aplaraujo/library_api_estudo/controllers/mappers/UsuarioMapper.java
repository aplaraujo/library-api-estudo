package io.github.aplaraujo.library_api_estudo.controllers.mappers;

import io.github.aplaraujo.library_api_estudo.controllers.dto.UsuarioDTO;
import io.github.aplaraujo.library_api_estudo.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioDTO dto);
}
