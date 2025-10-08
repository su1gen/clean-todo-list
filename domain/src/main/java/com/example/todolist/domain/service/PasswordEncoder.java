package com.example.todolist.domain.service;

/**
 * Порт для шифрования паролей.
 * Бизнес-логика не должна зависеть от конкретной реализации (BCrypt).
 */
public interface PasswordEncoder {

    /**
     * Зашифровать пароль
     */
    String encode(String rawPassword);

    /**
     * Проверить соответствие пароля хешу
     */
    boolean matches(String rawPassword, String encodedPassword);
}
