package io.github.aplaraujo.library_api_estudo.controllers;

import io.github.aplaraujo.library_api_estudo.controllers.dto.AutorDTO;
import io.github.aplaraujo.library_api_estudo.controllers.dto.ErroResposta;
import io.github.aplaraujo.library_api_estudo.controllers.mappers.AutorMapper;
import io.github.aplaraujo.library_api_estudo.exceptions.OperacaoNaoPermitidaException;
import io.github.aplaraujo.library_api_estudo.exceptions.RegistroDuplicadoException;
import io.github.aplaraujo.library_api_estudo.model.Autor;
import io.github.aplaraujo.library_api_estudo.services.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/autores") // http://localhost:8080/autores
@RequiredArgsConstructor
public class AutorController { // Camada de entrada de dados do sistema

    private final AutorService autorService;
    private final AutorMapper autorMapper;

    // Formas de criar uma requisição
    // Response Entity - classe que representa uma resposta a uma requisição
//    @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto) {
        try {
            Autor autor = autorMapper.toEntity(dto);
            autorService.salvar(autor);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(autor.getId()).toUri();
            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        return autorService
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = autorMapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> excluirAutor(@PathVariable String id) {
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autor = autorService.obterPorId(idAutor);
            if (autor.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            autorService.excluir(autor.get());
            return ResponseEntity.noContent().build();
        } catch (OperacaoNaoPermitidaException e) {
            var erro = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }

    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "nome", required = false) String nome, @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> lista = autorService.pesquisaByExample(nome,nacionalidade);
        List<AutorDTO> dto = lista.stream().map(autorMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody @Valid AutorDTO dto) {
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autor = autorService.obterPorId(idAutor);

            if (autor.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var resultado = autor.get();
            resultado.setNome(dto.nome());
            resultado.setDataNascimento(dto.dataNascimento());
            resultado.setNacionalidade(dto.nacionalidade());
            autorService.atualizar(resultado);
            return ResponseEntity.noContent().build();
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }

    }
}
