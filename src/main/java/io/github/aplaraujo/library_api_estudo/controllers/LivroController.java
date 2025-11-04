package io.github.aplaraujo.library_api_estudo.controllers;

import io.github.aplaraujo.library_api_estudo.controllers.dto.CadastroLivroDTO;
import io.github.aplaraujo.library_api_estudo.controllers.dto.ErroResposta;
import io.github.aplaraujo.library_api_estudo.exceptions.RegistroDuplicadoException;
import io.github.aplaraujo.library_api_estudo.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/livros")
@RequiredArgsConstructor
public class LivroController {
    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        try {
            // Mapear dto para a entidade
            // Enviar a entidade para o serviço, validação e gravação na base
            // Criar url de acesso dos dados do livro
            // Retornar código criado com cabeçalho
            return ResponseEntity.ok(dto);
        } catch (RegistroDuplicadoException e) {
            var erro = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }
}
