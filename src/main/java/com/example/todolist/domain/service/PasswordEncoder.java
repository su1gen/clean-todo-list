package com.example.todolist.domain.service;

import com.example.todolist.domain.model.Password;

/**
 * Порт для шифрования паролей.
 * Бизнес-логика не должна зависеть от конкретной реализации (BCrypt).
 */
public interface PasswordEncoder {

    /**
     * Зашифровать пароль
     */
    Password encode(Password rawPassword);

    /**
     * Проверить соответствие пароля хешу
     */
    boolean matches(Password rawPassword, Password encodedPassword);
}
