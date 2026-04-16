package com.example.avaliadorfilmes.filme.mapper;

import com.example.avaliadorfilmes.filme.dto.FilmeRequestDTO;
import com.example.avaliadorfilmes.filme.dto.FilmeResponseDTO;
import com.example.avaliadorfilmes.filme.model.Filme;
import org.mapstruct.Mapper;

// "Mapper" Gera uma implementação da interface automática a partir dos métodos citados
@Mapper(componentModel = "spring")
public interface FilmeMapper {

    // Cria um filme com base nas DTOs recebidas
    Filme toEntity(FilmeRequestDTO filmeDTO);

    // Cria uma resposta com base no filme recebido
    FilmeResponseDTO toResponseDTO(Filme filme);
}