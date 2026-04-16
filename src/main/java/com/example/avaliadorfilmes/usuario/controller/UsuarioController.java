package com.example.avaliadorfilmes.usuario.controller;
import com.example.avaliadorfilmes.usuario.dto.UsuarioRequestDTO;
import com.example.avaliadorfilmes.usuario.dto.UsuarioResponseDTO;
import com.example.avaliadorfilmes.usuario.mapper.UsuarioMapper;
import com.example.avaliadorfilmes.usuario.model.Usuario;
import com.example.avaliadorfilmes.usuario.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    // Cria uma variavel final do tipo usuarioService
    private final UsuarioService usuarioService;

    // Cria uma variavel final do tipo mapper
    private final UsuarioMapper mapper;

    // Faz a injeção de dependências e implementa as responsabilidades nas variaveis criadas
    public UsuarioController(UsuarioService usuarioService, UsuarioMapper mapper){
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }

    // Endpoint para criar usuários
    @PostMapping("/registrar")
    public UsuarioResponseDTO criarUsuario(@RequestBody UsuarioRequestDTO usuarioDTO){
        // Cria o objeto usuário a partir da DTO
        Usuario usuario = mapper.toEntity(usuarioDTO);

        // Salva o objeto
        Usuario usuarioSalvo = usuarioService.criarUsuario(usuario);

        // Retorna o response usando o mapper
        return mapper.toResponseDTO(usuarioSalvo);
    }

    // Endpoint para listar todos os usuarios
    @GetMapping
    public List<UsuarioResponseDTO> listarTodosUsuarios() {
        return usuarioService.listarTodosUsuarios()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    // Endpoint para listar usuarios por id
    @GetMapping("/{id}")
    public UsuarioResponseDTO buscarUsuarioId(@PathVariable Long id){
        Usuario usuario = usuarioService.buscarUsuarioId(id);
        return mapper.toResponseDTO(usuario);
    }

    // Endpoint para atualizar usuario
    @PutMapping("/{id}")
    public UsuarioResponseDTO atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO novoUsuarioDTO){

        // Cria o objeto novoUsuario e atribui o DTO a ele
        Usuario novoUsuario = mapper.toEntity(novoUsuarioDTO);

        // Salva o objeto no banco de dados
        Usuario novoUsuarioSalvo = usuarioService.atualizarUsuario(id, novoUsuario);

        // Retorna o novoUsuarioSalvo utilizando o mapper
        return mapper.toResponseDTO(novoUsuarioSalvo);

    }

    // Endpoint para excluir um usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirUsuario(@PathVariable Long id){
        usuarioService.excluirUsuario(id);
        return ResponseEntity.ok("Usuario deletado com sucesso");
    }

    // Endpoint para buscar email, username ou nome
    @GetMapping("/buscar")
    public List<UsuarioResponseDTO> buscarUsuarios(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String nome
    ) {

        List<Usuario> usuarios;

        if (email != null && !email.isBlank()) {
            usuarios = List.of(usuarioService.buscarUsuarioEmail(email));
        } else if (username != null && !username.isBlank()) {
            usuarios = List.of(usuarioService.buscarUsuarioUsername(username));
        } else if (nome != null && !nome.isBlank()) {
            usuarios = usuarioService.buscarUsuarioNome(nome);
        } else {
            usuarios = usuarioService.listarTodosUsuarios();
        }

        return usuarios.stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

}
