package com.example.avaliadorfilmes.avaliacao.controller;
import com.example.avaliadorfilmes.avaliacao.dto.AvaliacaoRequestDTO;
import com.example.avaliadorfilmes.avaliacao.dto.AvaliacaoResponseDTO;
import com.example.avaliadorfilmes.avaliacao.mapper.AvaliacaoMapper;
import com.example.avaliadorfilmes.avaliacao.model.Avaliacao;
import com.example.avaliadorfilmes.filme.model.Filme;
import com.example.avaliadorfilmes.usuario.model.Usuario;
import com.example.avaliadorfilmes.avaliacao.service.AvaliacaoService;
import com.example.avaliadorfilmes.filme.service.FilmeService;
import com.example.avaliadorfilmes.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacao")
// Anotação que gera um construtor automatico para os campos finais declares
@RequiredArgsConstructor
public class AvaliacaoController {

    // Cria uma variavel final do tipo FilmeService
    private final FilmeService filmeService;

    // Cria uma variavel final do tipo UsuarioService
    private final UsuarioService usuarioService;

    // Cria uma variavel final do tipo AvaliacaoService
    private final AvaliacaoService avaliacaoService;

    // Cria uma variavel final do tipo mapper
    private final AvaliacaoMapper mapper;

    // Endpoint para criar avaliações
    @PostMapping
    public AvaliacaoResponseDTO criarAvaliacao(@RequestBody AvaliacaoRequestDTO avaliacaoDTO){

        // Busca entidades
        Filme filme = filmeService.buscarFilmeId(avaliacaoDTO.getFilmeId());

        // Busca o email pela autentificação
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        // Busca o usuário pelo email
        Usuario usuario = usuarioService.buscarUsuarioEmail(email);

        // Monta objeto Avaliacao
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setFilme(filme);
        avaliacao.setUsuario(usuario);
        avaliacao.setComentario(avaliacaoDTO.getComentario());
        avaliacao.setNota(avaliacaoDTO.getNota());

        // Salva
        Avaliacao avaliacaoSalva = avaliacaoService.criarAvaliacao(avaliacao);

        // Retorna DTO
        return mapper.toResponseDTO(avaliacaoSalva);
    }

    // Endpoint para listar todas avaliacoes
    @GetMapping
    public List<AvaliacaoResponseDTO> listarTodasAvaliacoes() {
        return avaliacaoService.listarTodasAvaliacoes()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    // Endpoint para listar avaliacao por id
    @GetMapping("/{id}")
    public AvaliacaoResponseDTO buscarAvaliacaoId(@PathVariable Long id){
        Avaliacao avaliacao = avaliacaoService.buscaAvaliacaoID(id);
        return mapper.toResponseDTO(avaliacao);
    }

    // Endpoint para atualizar avaliacao
    @PutMapping("/{id}")
    public AvaliacaoResponseDTO atualizarAvaliacao(
            @PathVariable Long id,
            @RequestBody AvaliacaoRequestDTO novaAvaliacaoDTO){

        // Monta objeto Avaliacao
        Avaliacao novaAvaliacao = new Avaliacao();
        novaAvaliacao.setComentario(novaAvaliacaoDTO.getComentario());
        novaAvaliacao.setNota(novaAvaliacaoDTO.getNota());

        // Chama service
        Avaliacao avaliacaoSalva = avaliacaoService.atualizarAvaliacao(id, novaAvaliacao);

        return mapper.toResponseDTO(avaliacaoSalva);
    }

    // Endpoint para excluir uma avaliacao
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirAvaliacao(@PathVariable Long id){
        avaliacaoService.excluirAvaliacao(id);
        return ResponseEntity.ok("Avaliação deletada com sucesso");
    }

    // Endpoint para buscar avaliação por filme, nota ou usuario
    @GetMapping("/buscar")
    public List<AvaliacaoResponseDTO> buscarAvaliacoes(
            @RequestParam(required = false) Long filme,
            @RequestParam(required = false) Integer nota,
            @RequestParam(required = false) Long usuario
    ) {

        // Cria uma lista de avaliações
        List<Avaliacao> avaliacoes;

        // Aplica a lógica para preencher a lista
        if (filme != null) {
            Filme filmeEscolhido = filmeService.buscarFilmeId(filme);
            avaliacoes = avaliacaoService.buscaAvaliacaoFilme(filmeEscolhido);
        } else if (nota != null) {
            avaliacoes = avaliacaoService.buscarAvaliacaoNota(nota);
        } else if (usuario != null) {
            Usuario usuarioEscolhido = usuarioService.buscarUsuarioId(usuario);
            avaliacoes = avaliacaoService.buscarAvaliacaoUsuario(usuarioEscolhido);
        } else {
            avaliacoes = avaliacaoService.listarTodasAvaliacoes();
        }

        // Retorna a lista
        return avaliacoes.stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    // Endpoint para buscar avaliação por usuário e filme
    @GetMapping("/usuario/{usuarioId}/filme/{filmeId}")
    public AvaliacaoResponseDTO buscarPorUsuarioEFilme(
            @PathVariable Long usuarioId,
            @PathVariable Long filmeId) {

        Usuario usuario = usuarioService.buscarUsuarioId(usuarioId);
        Filme filme = filmeService.buscarFilmeId(filmeId);

        Avaliacao avaliacao = avaliacaoService
                .buscarAvaliacaoUsuarioFilme(usuario, filme);

        return mapper.toResponseDTO(avaliacao);
    }

}
