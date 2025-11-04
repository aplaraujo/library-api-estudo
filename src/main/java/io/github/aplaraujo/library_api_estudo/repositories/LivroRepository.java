package io.github.aplaraujo.library_api_estudo.repositories;

import io.github.aplaraujo.library_api_estudo.model.Autor;
import io.github.aplaraujo.library_api_estudo.model.GeneroLivro;
import io.github.aplaraujo.library_api_estudo.model.Livro;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    // select * from livro where titulo = titulo
    List<Livro> findByTitulo(String titulo);

    List<Livro> findByIsbn(String isbn);

    // select * from livro where titulo = ? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // select * from livro where titulo = ? or isbn = ?
    List<Livro> findByTituloOrIsbnOrderByTitulo(String titulo, String isbn);

    // select * from livro where data_publicacao between ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    // JPQL - referência às entidades e às propriedades da entidade
    @Query("select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listaLivros();

    @Query("select a from Livro l join l.autor a")
    List<Autor> listaAutoresDosLivros();

    @Query("select distinct l.titulo from Livro l")
    List<String> listaNomesDiferentesLivros();

    @Query("""
            select l.genero
            from Livro l
            join l.autor a
            where a.nacionalidade = 'Brasileira'
            order by l.genero
            """)
    List<String> listaGenerosAutoresBrasileiros();

    // OPÇÃO 1: Com ordenação fixa
    @Query("select l from Livro l where l.genero = :genero order by l.dataPublicacao")
    List<Livro> findByGeneroOrdenadoPorData(@Param("genero") GeneroLivro generoLivro);

    // OPÇÃO 2: Com ordenação dinâmica usando Sort (RECOMENDADO)
    @Query("select l from Livro l where l.genero = :genero")
    List<Livro> findByGenero(@Param("genero") GeneroLivro generoLivro, Sort sort);

    // OPÇÃO 3: Query Method sem @Query (também aceita Sort)
    List<Livro> findByGeneroOrderByDataPublicacao(GeneroLivro genero);

    @Modifying // Anotação para operações de escrita
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    @Modifying // Anotação para operações de escrita
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate novaData);

    boolean existsByAutor(Autor autor);
}
