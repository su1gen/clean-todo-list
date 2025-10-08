package com.example.todolist.application.dto;

/**
 * DTO для ответа после успешной аутентификации.
 * НЕ содержит сам токен (он будет в HttpOnly cookie).
 */
public record AuthResponse(
        Long userId,
        String email
) {
}
