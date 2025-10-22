package com.example.todolist.domain.model.user;

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
    private final UserId id;
    private final Email email;
    private final HashedPassword password;
    private final LocalDateTime createdAt;

    // Полный конструктор (для восстановления из БД)
    public User(UserId id, Email email, HashedPassword password, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }

    // Бизнес-метод: изменение пароля
    public User changePassword(HashedPassword newPassword) {
        return new User(this.id, this.email, newPassword, this.createdAt);
    }

    // Getters
    public UserId getId() { return id; }
    public Email getEmail() { return email; }
    public HashedPassword getPassword() { return password; }
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
