package com.example.avaliadorfilmes.filme.service;

import com.example.avaliadorfilmes.filme.model.Filme;
import com.example.avaliadorfilmes.filme.repository.FilmeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
// Anotação que gera um construtor automatico para os campos finais declares
@RequiredArgsConstructor
public class FilmeService {

    // Define uma variável final do tipo FilmeRepository
    private final FilmeRepository repository;

    /* Faz a injeção de depedência atribuindo o repositório de filme a variavel criada
    public FilmeService(FilmeRepository repository){
        this.repository = repository;
    }
     */

    // Cria método para listar todos os filmes
    public List<Filme> listarTodosFilmes(){
        return repository.findAll();
    }

    // Cria método para salvar um filme
    public Filme criarFilme(Filme filme){
        if (filme.getTitulo() == null || filme.getTitulo().isBlank()) {
            throw new RuntimeException("O Titulo é obrigatório");
        }
        return repository.save(filme);
    }

    // Busca Filme por ID
    public Filme buscarFilmeId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));
    }

    // Método para atualizar filme
    public Filme atualizarFilme(Long id, Filme novoFilme){
        Filme filme = buscarFilmeId(id);
        filme.setTitulo(novoFilme.getTitulo());
        filme.setSinopse(novoFilme.getSinopse());
        filme.setAnoLancamento(novoFilme.getAnoLancamento());
        return repository.save(filme);
    }

    // Deleta Filme
    public void deletarFilme(Long id){
        if (!repository.existsById(id)) {
            throw new RuntimeException("Filme não encontrado");
        }
        repository.deleteById(id);
    }

    // --------------------------------------------------
    // Métodos criados na aplicação
    // --------------------------------------------------

    // Método para buscar filme por título
    public List<Filme> buscarFilmeTitulo(String titulo){
        return repository.findByTituloContainingIgnoreCase(titulo);
    }

    // Método para buscar filme por ano
    public List<Filme> buscarFilmeAnoLancamento(Integer ano){
        return repository.findByAnoLancamento(ano);
    }

}
