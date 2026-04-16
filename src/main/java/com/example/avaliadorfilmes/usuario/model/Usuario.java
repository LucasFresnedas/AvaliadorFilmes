package com.example.avaliadorfilmes.usuario.model;

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
@Table(
        name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "username")
        }
)
public class Usuario {

    // Declaração dos atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Anotação que impede nulos
    @Column(nullable = false)
    private String nome;

    // Anotação que impede nulos
    @Column(nullable = false)
    private String sobrenome;

    // Anotação que impede nulos
    @Column(nullable = false)
    private String username;

    // Anotação que impede nulos
    @Column(nullable = false)
    private String email;

    // Anotação que impede nulos
    @Column(nullable = false)
    private String senha;

    // Anotação que define o nome da coluna no banco de dados
    @Column(name = "mensagem_usuario")
    private String mensagemUsuario;

}
