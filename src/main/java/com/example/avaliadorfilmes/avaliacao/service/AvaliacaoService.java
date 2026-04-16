package com.example.avaliadorfilmes.avaliacao.service;

import com.example.avaliadorfilmes.avaliacao.model.Avaliacao;
import com.example.avaliadorfilmes.filme.model.Filme;
import com.example.avaliadorfilmes.usuario.model.Usuario;
import com.example.avaliadorfilmes.avaliacao.repository.AvaliacaoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AvaliacaoService {

    // Define uma variável final do tipo UsuarioRepository
    private final AvaliacaoRepository repository;

    // Injeção de dependência, atribuição do repository a variavel criada
    public AvaliacaoService(AvaliacaoRepository repository){
        this.repository = repository;
    }

    // Método para listar todas avaliações
    public List<Avaliacao> listarTodasAvaliacoes(){
        return repository.findAll();
    }

    // Método para cadastrar uma avaliação
    public Avaliacao criarAvaliacao(Avaliacao avaliacao){

        // Validação sobre nulos
        if (avaliacao.getUsuario() == null) {
            throw new RuntimeException("Usuário é obrigatório");
        }

        if (avaliacao.getFilme() == null) {
            throw new RuntimeException("Filme é obrigatório");
        }

        if (avaliacao.getNota() == null || avaliacao.getNota() < 0 || avaliacao.getNota() > 10) {
            throw new RuntimeException("Nota deve ser entre 0 e 10");
        }

        // Validação para saber se o usuário já avaliou ou não
        if (repository.existsByUsuarioAndFilme(
                avaliacao.getUsuario(),
                avaliacao.getFilme())) {

            throw new RuntimeException("Usuário já avaliou este filme");
        }

        // Define data e horário da avaliação
        avaliacao.setDataAvaliacao(LocalDateTime.now());

        return repository.save(avaliacao);
    }

    // Método para buscar uma avaliação por ID
    public Avaliacao buscaAvaliacaoID(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));
    }

    // Método para atualizar avaliação
    public Avaliacao atualizarAvaliacao(Long id, Avaliacao novaAvaliacao){
        Avaliacao avaliacao = buscaAvaliacaoID(id);
        avaliacao.setComentario(novaAvaliacao.getComentario());
        if (novaAvaliacao.getNota() != null) {
            if (novaAvaliacao.getNota() < 0 || novaAvaliacao.getNota() > 10) {
                throw new RuntimeException("Nota deve ser entre 0 e 10");
            }
            avaliacao.setNota(novaAvaliacao.getNota());
        }
        return repository.save(avaliacao);
    }

    // Método para excluir uma avaliação
    public void excluirAvaliacao(Long id){
        if (!repository.existsById(id)) {
            throw new RuntimeException("Avaliação não encontrada");
        }
        repository.deleteById(id);
    }

    // Método para buscar avaliação por filme
    public List<Avaliacao> buscaAvaliacaoFilme(Filme filme){
        return repository.findByFilme(filme, Sort.by("nota").descending());
    }

    // Método para buscar avaliação por usuário
    public List<Avaliacao> buscarAvaliacaoUsuario(Usuario usuario){
        return repository.findByUsuario(usuario);
    }

    // Método para buscar avaliação por nota
    public List<Avaliacao> buscarAvaliacaoNota(Integer nota){
        return repository.findByNota(nota);
    }

    // Método para buscar avaliação por usuário e filme
    public Avaliacao buscarAvaliacaoUsuarioFilme(Usuario usuario, Filme filme){
        return repository.findByUsuarioAndFilme(usuario, filme)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));
    }
}
