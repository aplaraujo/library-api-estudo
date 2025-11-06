package io.github.aplaraujo.library_api_estudo.controllers;

import io.github.aplaraujo.library_api_estudo.controllers.dto.CadastroLivroDTO;
import io.github.aplaraujo.library_api_estudo.controllers.dto.PesquisaLivroDTO;
import io.github.aplaraujo.library_api_estudo.controllers.mappers.LivroMapper;
import io.github.aplaraujo.library_api_estudo.model.GeneroLivro;
import io.github.aplaraujo.library_api_estudo.model.Livro;
import io.github.aplaraujo.library_api_estudo.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {
    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
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

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<PesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id) {
        return livroService.obterPorId(UUID.fromString(id)).map(livro -> {
            var dto = livroMapper.toDTO(livro);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> excluirLivro(@PathVariable String id) {
        var livroId = UUID.fromString(id);
        return livroService.obterPorId(livroId).map(livro -> {
            livroService.excluir(livro);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Page<PesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
            ) {
        Page<Livro> resultado = livroService.pesquisa(isbn, nomeAutor, titulo, genero, anoPublicacao, pagina, tamanhoPagina);
        Page<PesquisaLivroDTO> pesquisaLivroDTOPage = resultado.map(livroMapper::toDTO);

        return ResponseEntity.ok(pesquisaLivroDTOPage);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid CadastroLivroDTO dto) {
        return livroService.obterPorId(UUID.fromString(id)).map(livro -> {
            Livro entity = livroMapper.toEntity(dto);
            livro.setDataPublicacao(entity.getDataPublicacao());
            livro.setIsbn(entity.getIsbn());
            livro.setPreco(entity.getPreco());
            livro.setGenero(entity.getGenero());
            livro.setTitulo(entity.getTitulo());

            livroService.atualizar(livro);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
