package com.example.avaliadorfilmes.avaliacao.mapper;

import com.example.avaliadorfilmes.avaliacao.dto.AvaliacaoResponseDTO;
import com.example.avaliadorfilmes.avaliacao.model.Avaliacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AvaliacaoMapper {

    @Mapping(source = "filme.titulo", target = "filmeTitulo")
    @Mapping(source = "usuario.username", target = "usuarioUsername")
    AvaliacaoResponseDTO toResponseDTO(Avaliacao avaliacao);
}