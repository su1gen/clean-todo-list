package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.AuthResponse;
import com.example.todolist.application.dto.LoginRequest;
import com.example.todolist.application.dto.RegisterRequest;
import com.example.todolist.application.usecase.LoginUser;
import com.example.todolist.application.usecase.RegisterUser;
import com.example.todolist.infrastructure.security.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST контроллер для аутентификации.
 * <p>
 * Эндпоинты:
 * - POST /api/auth/register - регистрация
 * - POST /api/auth/login - логин
 * - POST /api/auth/logout - выход
 */
@RestController
@RequestMapping(ApiPaths.AUTH)
public class AuthController {

    private final RegisterUser registerUserUseCase;
    private final LoginUser loginUserUseCase;
    private final JwtService jwtService;

    public AuthController(
            RegisterUser registerUser,
            LoginUser loginUserUseCase,
            JwtService jwtService
    ) {
        this.registerUserUseCase = registerUser;
        this.loginUserUseCase = loginUserUseCase;
        this.jwtService = jwtService;
    }

    /**
     * POST /api/auth/register
     * Регистрация нового пользователя
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody @Valid RegisterRequest request,
            HttpServletResponse response
    ) {
        // 1. Выполнить регистрацию через use case
        AuthResponse authResponse = registerUserUseCase.execute(request);

        // 2. Генерировать JWT токен
        String token = jwtService.generateToken(
                authResponse.email(),
                authResponse.userId()
        );

        // 3. Установить токен в HttpOnly cookie
        setJwtCookie(response, token);

        // 4. Вернуть ответ (без токена в теле!)
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    /**
     * POST /api/auth/login
     * Вход в систему
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid LoginRequest request,
            HttpServletResponse response
    ) {
        // 1. Выполнить аутентификацию через use case
        AuthResponse authResponse = loginUserUseCase.execute(request);

        // 2. Генерировать JWT токен
        String token = jwtService.generateToken(
                authResponse.email(),
                authResponse.userId()
        );

        // 3. Установить токен в HttpOnly cookie
        setJwtCookie(response, token);

        // 4. Вернуть ответ
        return ResponseEntity.ok(authResponse);
    }

    /**
     * POST /api/auth/logout
     * Выход из системы (удаление cookie)
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // Удаляем JWT cookie
        Cookie cookie = new Cookie(jwtService.getCookieName(), null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // В production ОБЯЗАТЕЛЬНО true
        cookie.setPath("/");
        cookie.setMaxAge(0); // Удаляем cookie
        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }

    /**
     * Вспомогательный метод для установки JWT в HttpOnly cookie
     */
    private void setJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(jwtService.getCookieName(), token);
        cookie.setHttpOnly(true); // Защита от XSS
//        cookie.setSecure(true);   // HTTPS only (в dev можно false)
        cookie.setPath("/");      // Доступна для всего приложения
        cookie.setMaxAge((int) (jwtService.getExpirationMs() / 1000)); // В секундах
//        .sameSite("Lax") ???

        // SameSite=Strict для защиты от CSRF
        // В Spring Boot 3.x это нужно делать через ResponseCookie или вручную
        response.addCookie(cookie);
    }
}
