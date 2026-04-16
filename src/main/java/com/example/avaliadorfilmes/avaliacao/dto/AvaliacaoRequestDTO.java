package com.example.avaliadorfilmes.avaliacao.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AvaliacaoRequestDTO {

    @NotNull
    private Long filmeId;

    private String comentario;

    @NotNull
    private Integer nota;
}
