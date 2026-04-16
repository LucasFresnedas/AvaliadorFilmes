package com.example.avaliadorfilmes.filme.model;

// Importações Padrão
import jakarta.persistence.*;

// Importações Lombok
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Anotação para getters and setters
@Entity // Define a classe como entidade
@AllArgsConstructor // Anotação para construtor com todos campos
@NoArgsConstructor // Anotação para gerar um construtor vazio

public class Filme {

    // Declaração dos atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Anotação que impede nulos
    @Column(nullable = false)
    private String titulo;

    private String sinopse;

    // Anotação que define o nome da coluna no banco de dados
    @Column(name = "ano_lancamento")
    private Integer anoLancamento;

}
