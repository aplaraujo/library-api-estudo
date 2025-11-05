package io.github.aplaraujo.library_api_estudo.controllers;

import io.github.aplaraujo.library_api_estudo.controllers.dto.CadastroLivroDTO;
import io.github.aplaraujo.library_api_estudo.controllers.dto.ErroResposta;
import io.github.aplaraujo.library_api_estudo.controllers.mappers.LivroMapper;
import io.github.aplaraujo.library_api_estudo.exceptions.RegistroDuplicadoException;
import io.github.aplaraujo.library_api_estudo.model.Livro;
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
public class LivroController implements GenericController {
    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        // Mapear dto para a entidade
        Livro livro = livroMapper.toEntity(dto);
        // Enviar a entidade para o serviço, validação e gravação na base
        livroService.salvar(livro);
        // Criar url de acesso dos dados do livro
        // Retornar código criado com cabeçalho
        var url = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(url).build();

    }
}
