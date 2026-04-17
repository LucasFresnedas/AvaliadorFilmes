package com.example.avaliadorfilmes.auth.controller;

import com.example.avaliadorfilmes.auth.dto.LoginRequestDTO;
import com.example.avaliadorfilmes.auth.dto.LoginResponseDTO;
import com.example.avaliadorfilmes.auth.service.JwtService;
import com.example.avaliadorfilmes.usuario.model.Usuario;
import com.example.avaliadorfilmes.usuario.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    public AuthController(UsuarioService usuarioService,
                          JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginDTO) {

        Usuario usuario = usuarioService.autenticar(
                loginDTO.getEmail(),
                loginDTO.getSenha()
        );

        String token = jwtService.gerarToken(usuario.getEmail(), usuario.getId());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}