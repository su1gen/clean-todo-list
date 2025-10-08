package com.example.todolist.domain.exception;

/**
 * Исключение для неверных учетных данных.
 * НЕ раскрываем, что именно неверно (email или пароль).
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid email or password");
    }
}
