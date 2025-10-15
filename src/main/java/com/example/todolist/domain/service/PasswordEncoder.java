package com.example.todolist.domain.service;

import com.example.todolist.domain.model.HashedPassword;
import com.example.todolist.domain.model.Password;

/**
 * Порт для шифрования паролей.
 * Бизнес-логика не должна зависеть от конкретной реализации (BCrypt).
 */
public interface PasswordEncoder {

    /**
     * Зашифровать пароль
     */
    HashedPassword encode(Password rawPassword);

    /**
     * Проверить соответствие пароля хешу
     */
    boolean matches(Password rawPassword, HashedPassword encodedPassword);
}
