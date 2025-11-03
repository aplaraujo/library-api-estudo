package io.github.aplaraujo.library_api_estudo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public") // O nome precisa ser o mesmo da tabela criada no banco de dados
@Getter // Anotação que gera métodos "getter" em tempo de compilação
@Setter // Anotação que gera métodos "setter" em tempo de compilação
@NoArgsConstructor
@ToString(exclude = "livros")
public class Autor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Um livro para um autor
    private List<Livro> livros;
}
