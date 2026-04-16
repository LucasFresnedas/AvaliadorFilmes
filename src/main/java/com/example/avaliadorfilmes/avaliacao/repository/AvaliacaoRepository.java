package com.example.avaliadorfilmes.avaliacao.repository;
import com.example.avaliadorfilmes.avaliacao.model.Avaliacao;
import com.example.avaliadorfilmes.filme.model.Filme;
import com.example.avaliadorfilmes.usuario.model.Usuario;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    // Método para buscar avaliações por filme
    List<Avaliacao> findByFilme(Filme filme, Sort sort);

    // Método para buscar avaliações pela nota
    List<Avaliacao> findByNota(Integer nota);

    // Método para buscar avaliações pelo usuário
    List<Avaliacao> findByUsuario(Usuario usuario);

    // Método para buscar avaliações de um usuário por determinado filme
    Optional<Avaliacao> findByUsuarioAndFilme(Usuario usuario, Filme filme);

    // Validação pra saber se o usuário já avaliou o filme
    boolean existsByUsuarioAndFilme(Usuario usuario, Filme filme);
}
