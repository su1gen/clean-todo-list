package com.example.todolist.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO для регистрации пользователя.
 * Используется на границе системы (от клиента к use case).
 */
public record RegisterRequest(

        @NotBlank(message = "Email не может быть пустым")
        @Email(message = "Некорректный формат email")
        String email,

        @NotBlank(message = "Пароль не может быть пустым")
        @Size(min = 6, max = 255, message = "Пароль должен быть от 6 до 255 символов")
        String password
) {
}
