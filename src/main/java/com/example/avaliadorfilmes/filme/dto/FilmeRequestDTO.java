package com.example.avaliadorfilmes.filme.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class FilmeRequestDTO {

    @NotBlank
    private String titulo;
    @NotBlank
    private String sinopse;
    @NotNull
    private Integer anoLancamento;
}
