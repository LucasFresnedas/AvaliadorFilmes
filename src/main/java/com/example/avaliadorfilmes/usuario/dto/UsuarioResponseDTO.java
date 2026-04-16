package com.example.avaliadorfilmes.usuario.dto;
import lombok.Data;

@Data
public class UsuarioResponseDTO {

    // Na resposta não é necessário validações porque é uma saída e não um input
    private Long id;
    private String nome;
    private String sobrenome;
    private String username;
    private String email;
    private String mensagemUsuario;
}
