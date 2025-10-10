package com.example.todolist.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Доменная модель пользователя.
 * Это ядро бизнес-логики, независимое от БД и фреймворков.
 *
 * Принципы:
 * - Immutable где возможно (используем final для обязательных полей)
 * - Валидация в конструкторе или фабричных методах
 * - в ней не должно быть запросов в базу данных, такая проверка должна быть в сервисе - application слой
 * - домен должен быть чисто Java код
 * - Бизнес-методы находятся здесь
 */
public class User {
    private final Long id;
    private final String email;
    private final String password; // В идеале это должен быть Value Object
    private final LocalDateTime createdAt;

    // Полный конструктор (для восстановления из БД)
    public User(Long id, String email, String password, LocalDateTime createdAt) {
        this.id = id;
        this.email = validateEmail(email);
        this.password = validatePassword(password);
        this.createdAt = createdAt;
    }

    // Бизнес-валидация
    private String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return email.toLowerCase().trim();
    }

    private String validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        // Не проверяем длину для хеша, т.к. это может быть BCrypt хеш
        return password;
    }

    // Бизнес-метод: изменение пароля
    public User changePassword(String newPassword) {
        return new User(this.id, this.email, newPassword, this.createdAt);
    }

    // Getters
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // equals и hashCode по бизнес-ключу (email)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
