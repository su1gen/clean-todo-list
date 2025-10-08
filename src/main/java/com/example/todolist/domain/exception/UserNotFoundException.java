package com.example.todolist.domain.exception;

/**
 * Исключение бизнес-логики: пользователь не найден.
 * Это НЕ исключение БД, а доменное событие.
 * Эти исключения описывают бизнес-сценарии, а не технические ошибки БД.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("User not found: " + email);
    }

    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}