package com.example.todolist.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Доменная модель категории.
 *
 * Бизнес-правила:
 * - Категория принадлежит пользователю (userId)
 * - Title не может быть пустым
 * - Soft delete через deletedAt
 * - Удалённые категории не должны отображаться в обычных запросах
 */
public class Category {
    private final Long id;
    private final String title;
    private final Long userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    // Конструктор для создания новой категории
    public Category(String title, Long userId) {
        this.id = null;
        this.title = validateTitle(title);
        this.userId = validateUserId(userId);
        this.createdAt = LocalDateTime.now();
        this.deletedAt = null;
    }

    // Полный конструктор (для восстановления из БД)
    public Category(Long id, String title, Long userId, LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.id = id;
        this.title = validateTitle(title);
        this.userId = validateUserId(userId);
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    // Валидация title
    private String validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Category title cannot be empty");
        }
        if (title.length() > 255) {
            throw new IllegalArgumentException("Category title cannot exceed 255 characters");
        }
        return title.trim();
    }

    // Валидация userId
    private Long validateUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Category must belong to a user");
        }
        return userId;
    }

    // Бизнес-методы

    /**
     * Обновить название категории
     */
    public Category updateTitle(String newTitle) {
        return new Category(this.id, newTitle, this.userId, this.createdAt, this.deletedAt);
    }

    /**
     * Пометить категорию как удалённую (soft delete)
     */
    public Category markAsDeleted() {
        if (this.deletedAt != null) {
            throw new IllegalStateException("Category is already deleted");
        }
        return new Category(this.id, this.title, this.userId, this.createdAt, LocalDateTime.now());
    }

    /**
     * Восстановить удалённую категорию
     */
    public Category restore() {
        if (this.deletedAt == null) {
            throw new IllegalStateException("Category is not deleted");
        }
        return new Category(this.id, this.title, this.userId, this.createdAt, null);
    }

    /**
     * Проверка: удалена ли категория
     */
    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    /**
     * Проверка: принадлежит ли категория пользователю
     */
    public boolean belongsToUser(Long userId) {
        return Objects.equals(this.userId, userId);
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", userId=" + userId +
                ", deleted=" + isDeleted() +
                '}';
    }
}
