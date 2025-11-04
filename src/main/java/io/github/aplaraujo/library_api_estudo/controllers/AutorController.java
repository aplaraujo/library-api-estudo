package io.github.aplaraujo.library_api_estudo.controllers;

import io.github.aplaraujo.library_api_estudo.dto.AutorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/autores") // http://localhost:8080/autores

public class AutorController { // Camada de entrada de dados do sistema

    // Formas de criar uma requisição
    // Response Entity - classe que representa uma resposta a uma requisição
//    @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity salvar(@RequestBody AutorDTO dto) {
        return new ResponseEntity("Autor salvo com sucesso: " + dto, HttpStatus.CREATED);
    }
}
