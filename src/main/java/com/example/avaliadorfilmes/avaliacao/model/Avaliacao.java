package com.example.avaliadorfilmes.avaliacao.model;

// Importações Padrão
import com.example.avaliadorfilmes.filme.model.Filme;
import com.example.avaliadorfilmes.usuario.model.Usuario;
import jakarta.persistence.*;

// Importações Lombok
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data // Anotação para getters and setters
@Entity // Define a classe como entidade
@AllArgsConstructor // Anotação para construtor com todos campos
@NoArgsConstructor // Anotação para gerar um construtor vazio
@Table(
        name = "avaliacoes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"usuario_id", "filme_id"})
        }
)
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Define o relacionamento de 1 para muitos
    @ManyToOne
    @JoinColumn(name = "filme_id", nullable = false)
    private Filme filme;

    // Define o relacionamento de 1 para muitos
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Anotação que impede nulos
    @Column(nullable = false)
    private String comentario;

    // Anotação que impede nulos
    @Column(nullable = false)
    private Integer nota;

    // Adição da data de avaliação
    @Column(nullable = false)
    private LocalDateTime dataAvaliacao;
}
