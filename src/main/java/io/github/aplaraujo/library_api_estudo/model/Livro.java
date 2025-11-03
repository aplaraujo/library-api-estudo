package io.github.aplaraujo.library_api_estudo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data // Anotação que cria outras anotações do Lombok: @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor // Anotaçao que cria um construtor sem argumentos
@AllArgsConstructor // Anotação que cria um construtor com todas as propriedades
public class Livro {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2)
    private BigDecimal preco;

    // CascadeType.ALL - Qualquer operação que foi feita vai trazer o autor junto
    // Fetch - Como trazer as informações do autor em um relacionamento de muitos para um
    // FetchType.EAGER - comportamento padrão de um relacionamento de muitos para um
    // FetchType.LAZY - traz apenas os dados do livro, não traz os dados do autor
    @ManyToOne(fetch = FetchType.LAZY) // Muitos livros para um autor
    @JoinColumn(name = "id_autor", nullable = true)
    private Autor autor;

}
