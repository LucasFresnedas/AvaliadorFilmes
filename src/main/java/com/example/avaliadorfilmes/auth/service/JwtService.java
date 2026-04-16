package com.example.avaliadorfilmes.auth.service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

@Service
public class JwtService {

    // Cria a variavel da chave(assinatura) que precisa ter pelo menos 32 caracteres
    private final SecretKey key;

    // Seta a assinatura via variavel de ambiente
    public JwtService(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Define tempo de expiração
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora

    // Cria um token que espera o email e o id do usuário
    public String gerarToken(String email, Long userId) {

        // Retorna o token com essas informações compactadas
        return Jwts.builder()
                .subject(email)
                .claim("id", userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // Valida o Token
    public Claims validarToken(String token) {

        // Desmonta o token para verificar se ele está correto
        return Jwts.parser()
                // Verifica se o token contém a assinatura
                .verifyWith(key)
                // Finaliza a configuração do parser
                .build()
                // Verifica o resto do token(expiração, formatação etc)
                .parseSignedClaims(token)
                // Permite a entrega dos dados após validações
                .getPayload();
    }
}
