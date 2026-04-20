package com.example.avaliadorfilmes.filme.controller;
import com.example.avaliadorfilmes.filme.dto.FilmeRequestDTO;
import com.example.avaliadorfilmes.filme.dto.FilmeResponseDTO;
import com.example.avaliadorfilmes.filme.mapper.FilmeMapper;
import com.example.avaliadorfilmes.filme.model.Filme;
import com.example.avaliadorfilmes.filme.service.FilmeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/filmes")
// Anotação que gera um construtor automatico para os campos finais declares
@RequiredArgsConstructor
public class FilmeController {

    // Cria uma variavel final do tipo FilmeService
    private final FilmeService filmeService;

    // Cria uma variavel final do tipo Mapper
    private final FilmeMapper mapper;

    // Endpoint para criar filme
    @PostMapping
    public FilmeResponseDTO criarFilme(@RequestBody FilmeRequestDTO filmeDTO){

        // Cria um obejto filme a partir do DTO utilizando mapper
        Filme filme = mapper.toEntity(filmeDTO);

        // Salva o objeto no banco de dados
        Filme filmeSalvo = filmeService.criarFilme(filme);

        // Retorna um response DTO utilizando a conversão mapper
        return mapper.toResponseDTO(filmeSalvo);
    }

    // Endpoint para listar todos os filmes
    @GetMapping
    public List<FilmeResponseDTO> listarTodosFilmes() {
        return filmeService.listarTodosFilmes()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    // Endpoint para listar filme por id
    @GetMapping("/{id}")
    public FilmeResponseDTO buscarFilmeId(@PathVariable Long id){
        Filme filme = filmeService.buscarFilmeId(id);
        return mapper.toResponseDTO(filme);
    }

    // Endpoint para atualizar filme
    @PutMapping("/{id}")
    public FilmeResponseDTO atualizarFilme(@PathVariable Long id, @RequestBody FilmeRequestDTO novoFilmeDTO){

        // Cria o objeto novoFilme
        Filme novoFilme = mapper.toEntity(novoFilmeDTO);

        // Salva o objeto no banco de dados
        Filme novoFilmeSalvo = filmeService.atualizarFilme(id, novoFilme);

        // Retorna o novoFilmeSalvo utilizando o mapper
        return mapper.toResponseDTO(novoFilmeSalvo);

    }

    // Endpoint para excluir um filme
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirFilme(@PathVariable Long id){
        filmeService.deletarFilme(id);
        return ResponseEntity.ok("Filme deletado com sucesso");
    }

    // Endpoint de busca por titulo ou não
    @GetMapping("/buscar")
    public List<FilmeResponseDTO> buscarFilmes(
            // Defines os parâmetros como não obrigatórios
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer anoLancamento) {

        // Cria uma lista de objetos filmes
        List<Filme> filmes;

        // Implementa lógica de chamada de métodos
        if (titulo != null) {
            filmes = filmeService.buscarFilmeTitulo(titulo);
        } else if (anoLancamento != null) {
            filmes = filmeService.buscarFilmeAnoLancamento(anoLancamento);
        } else {
            filmes = filmeService.listarTodosFilmes();
        }

        // Retorna lista conforme lógica
        return filmes.stream()
                .map(mapper::toResponseDTO)
                .toList();
    }


}
