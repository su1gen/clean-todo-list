package com.example.todolist.infrastructure.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

/**
 * Сервис для работы с JWT токенами.
 *
 * Ответственность:
 * - Генерация токенов
 * - Валидация токенов
 * - Извлечение данных из токенов
 */
@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMs;
    private static final String COOKIE_NAME = "JWT";

    public JwtService(
            @Value("${app.jwt.secret}") String base64Secret,
            @Value("${app.jwt.expiration-ms}") long expirationMs
    ) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
        this.expirationMs = expirationMs;
    }

    /**
     * Генерация JWT токена для пользователя
     */
    public String generateToken(String email, Long userId) {
        Instant now = Instant.now();
        Date issued = Date.from(now);
        Date expiry = Date.from(now.plusMillis(expirationMs));

        return Jwts.builder()
                .subject(email) // Subject - это email
                .claim("userId", userId) // Добавляем userId в claims
                .issuedAt(issued)
                .expiration(expiry)
                .signWith(key)
                .compact();
    }

    /**
     * Валидация токена
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            // Логирование можно добавить здесь
            return false;
        }
    }

    /**
     * Извлечь email из токена
     */
    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Извлечь userId из токена
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("userId", Long.class);
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    public String getCookieName() {
        return COOKIE_NAME;
    }
}
