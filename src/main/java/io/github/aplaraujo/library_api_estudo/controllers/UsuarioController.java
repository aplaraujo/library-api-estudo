package io.github.aplaraujo.library_api_estudo.controllers;

import io.github.aplaraujo.library_api_estudo.controllers.dto.UsuarioDTO;
import io.github.aplaraujo.library_api_estudo.controllers.mappers.UsuarioMapper;
import io.github.aplaraujo.library_api_estudo.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto) {
        var usuario = usuarioMapper.toEntity(dto);
        usuarioService.salvar(usuario);
    }
}
