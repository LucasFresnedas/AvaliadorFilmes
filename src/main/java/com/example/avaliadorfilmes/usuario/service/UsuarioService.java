package com.example.avaliadorfilmes.usuario.service;

import com.example.avaliadorfilmes.usuario.model.Usuario;
import com.example.avaliadorfilmes.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;

@Service
public class UsuarioService {

    // Define uma variável final do tipo UsuarioRepository
    private final UsuarioRepository repository;

    // Cria uma variavel para receber os códigos da classe "BCryptPasswordEncoder"
    private final BCryptPasswordEncoder passwordEncoder;

    // Faz a injeção de depedências atribuindo o repositório de usuario a variavel criada e a classe Decoder a variavel criada
    public UsuarioService(UsuarioRepository repository, BCryptPasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para listar todos os usuarios
    public List<Usuario> listarTodosUsuarios(){
        return repository.findAll();
    }

    // Método para cadastrar usuários
    public Usuario criarUsuario(Usuario usuario){

        // Validações de Null -------
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new RuntimeException("Email é obrigatório");
        }

        if (usuario.getUsername() == null || usuario.getUsername().isBlank()) {
            throw new RuntimeException("Username é obrigatório");
        }

        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new RuntimeException("Senha é obrigatória");
        }

        // Validações de repetições ------
        if (repository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        if (repository.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("Username já cadastrado");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return repository.save(usuario);
    }

    // Busca Usuario por ID
    public Usuario buscarUsuarioId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Método para atualizar usuario
    public Usuario atualizarUsuario(Long id, Usuario novoUsuario){
        Usuario usuario = buscarUsuarioId(id);
        usuario.setNome(novoUsuario.getNome());
        usuario.setSobrenome(novoUsuario.getSobrenome());
        if (!usuario.getUsername().equals(novoUsuario.getUsername())) {
            if (repository.existsByUsername(novoUsuario.getUsername())) {
                throw new RuntimeException("Username já em uso");
            }
            usuario.setUsername(novoUsuario.getUsername());
        }
        if (!usuario.getEmail().equals(novoUsuario.getEmail())) {
            if (repository.existsByEmail(novoUsuario.getEmail())) {
                throw new RuntimeException("Email já em uso");
            }
            usuario.setEmail(novoUsuario.getEmail());
        }
        if (novoUsuario.getSenha() != null && !novoUsuario.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(novoUsuario.getSenha()));
        }
        usuario.setMensagemUsuario(novoUsuario.getMensagemUsuario());
        return repository.save(usuario);
    }

    // Método para excluir usuário
    public void excluirUsuario(Long id){
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuario não encontrado");
        }
        repository.deleteById(id);
    }

    // Métodos criados no repository

    // Método para buscar usuário pelo email
    public Usuario buscarUsuarioEmail(String email){
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Método para buscar usuário pelo username
    public Usuario buscarUsuarioUsername(String username){
        return repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Método para buscar usuário pelo nome
    public List<Usuario> buscarUsuarioNome(String nome){
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    // Autenticador
    public Usuario autenticar(String email, String senha) {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        return usuario;
    }
}

