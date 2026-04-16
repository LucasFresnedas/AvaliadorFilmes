package com.example.avaliadorfilmes.usuario.mapper;
import com.example.avaliadorfilmes.usuario.dto.UsuarioRequestDTO;
import com.example.avaliadorfilmes.usuario.dto.UsuarioResponseDTO;
import com.example.avaliadorfilmes.usuario.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // Cria um filme com base nas DTOs recebidas
    Usuario toEntity(UsuarioRequestDTO usuarioDTO);

    // Cria uma resposta com base no filme recebido
    UsuarioResponseDTO toResponseDTO(Usuario usuario);
}
