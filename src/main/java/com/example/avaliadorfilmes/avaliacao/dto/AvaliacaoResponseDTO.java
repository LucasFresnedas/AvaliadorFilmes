package com.example.avaliadorfilmes.avaliacao.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AvaliacaoResponseDTO {

    // Na resposta não é necessário validações porque é uma saída e não um input
    private String filmeTitulo;
    private String usuarioUsername;
    private String comentario;
    private Integer nota;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataAvaliacao;
}
