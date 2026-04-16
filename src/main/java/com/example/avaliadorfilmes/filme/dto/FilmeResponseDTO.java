package com.example.avaliadorfilmes.filme.dto;
import lombok.Data;

@Data
public class FilmeResponseDTO {

    // Na resposta não é necessário validações porque é uma saída e não um input
    private Long id;
    private String titulo;
    private String sinopse;
    private Integer anoLancamento;
}
