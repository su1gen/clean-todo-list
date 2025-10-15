package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.LoginRequest;
import com.example.todolist.application.dto.TokenDto;
import com.example.todolist.application.inbound.user.LoginUser;
import com.example.todolist.infrastructure.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Use Case: Аутентификация пользователя.
 * <p>
 * Бизнес-правила:
 * 1. Пользователь должен существовать
 * 2. Пароль должен совпадать с хешем
 */
@Component
class LoginUserCase implements LoginUser {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    LoginUserCase(AuthenticationManager authManager, JwtService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @Override
    public TokenDto execute(LoginRequest loginRequest) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );
            String token = jwtService.generateToken(loginRequest.email());
            long expiresAt = Instant.now().toEpochMilli() + Long.parseLong(
                    String.valueOf(jwtService.getExpirationMs())
            );
            return new TokenDto(token, expiresAt);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadCredentialsException("Wrong credentials");
        }
    }
}
