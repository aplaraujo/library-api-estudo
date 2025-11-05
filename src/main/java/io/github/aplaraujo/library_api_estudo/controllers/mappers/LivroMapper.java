package io.github.aplaraujo.library_api_estudo.controllers.mappers;

import io.github.aplaraujo.library_api_estudo.controllers.dto.CadastroLivroDTO;
import io.github.aplaraujo.library_api_estudo.controllers.dto.PesquisaLivroDTO;
import io.github.aplaraujo.library_api_estudo.model.Livro;
import io.github.aplaraujo.library_api_estudo.repositories.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {
    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    @Mapping(source = "autor", target = "autorDTO")
    public abstract PesquisaLivroDTO toDTO(Livro livro);
}
