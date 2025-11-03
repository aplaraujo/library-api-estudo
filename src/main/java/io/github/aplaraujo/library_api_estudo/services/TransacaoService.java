package io.github.aplaraujo.library_api_estudo.services;

import io.github.aplaraujo.library_api_estudo.model.Autor;
import io.github.aplaraujo.library_api_estudo.model.GeneroLivro;
import io.github.aplaraujo.library_api_estudo.model.Livro;
import io.github.aplaraujo.library_api_estudo.repositories.AutorRepository;
import io.github.aplaraujo.library_api_estudo.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void salvarLivroComFoto() {
        // Salva o livro
        // livroRepository.save(livro)

        // Obter o id do livro = livro.getId();
        // var id = livro.getLivro();

        // Salvar a foto do livro
        // bucketService.salvar(livro.getFoto(), id + ".png");

        // Atualizar o nome do arquivo salvo
        // livro.setNomeArquivoFoto(id + ".png")
    }

    @Transactional
    public void atualizacaoSemAtualizar() {
       var livro = livroRepository.findById(UUID.fromString("937599c0-e7d9-48f9-942d-cdf01cde8ee8")).orElse(null);
       livro.setTitulo("O caso do assassinato no iate");
    }

    // Método para executar a transação (tem que ser público)
    @Transactional
    public void executar() {
        // Salvar o autor
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1965, 12, 1));

        autorRepository.save(autor);

        // Salvar o livro
        Livro livro1 = new Livro();
        livro1.setIsbn("0860249271");
        livro1.setPreco(BigDecimal.valueOf(200));
        livro1.setGenero(GeneroLivro.ROMANCE);
        livro1.setTitulo("Como eu conhecei o Rei");
        livro1.setDataPublicacao(LocalDate.of(2010, 1, 1));
        livro1.setAutor(autor);

        livroRepository.save(livro1);

        if (autor.getNome().equals("José")) {
            throw new RuntimeException("Rollback!!!");
        }
    }
}
