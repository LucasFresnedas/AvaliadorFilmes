package com.example.avaliadorfilmes.filme.repository;
import com.example.avaliadorfilmes.filme.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    // Método para encontrar o filme pelo titulo
    List<Filme> findByTituloContainingIgnoreCase(String titulo);
    // Método para encontrar filme pelo ano de lançamento
    List<Filme> findByAnoLancamento(Integer ano);
}
