package com.example.avaliadorfilmes.usuario.repository;
import com.example.avaliadorfilmes.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método que busca usuários pelo email
    Optional<Usuario> findByEmail(String email);

    // Método que busca usuários pelo username
    Optional<Usuario> findByUsername(String username);

    // Método que busca usuários pelo nome
    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    // ---------------------------------
    // Validações para cadastrar usuário

    // Verifica se existe o email
    boolean existsByEmail(String email);

    // Verifica se existe o username
    boolean existsByUsername(String username);
}
