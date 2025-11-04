package io.github.aplaraujo.library_api_estudo.controllers;

import io.github.aplaraujo.library_api_estudo.dto.AutorDTO;
import io.github.aplaraujo.library_api_estudo.model.Autor;
import io.github.aplaraujo.library_api_estudo.services.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/autores") // http://localhost:8080/autores

public class AutorController { // Camada de entrada de dados do sistema

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    // Formas de criar uma requisição
    // Response Entity - classe que representa uma resposta a uma requisição
//    @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO dto) {
        Autor autor = dto.mapearParaAutor();
        autorService.salvar(autor);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(autor.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
