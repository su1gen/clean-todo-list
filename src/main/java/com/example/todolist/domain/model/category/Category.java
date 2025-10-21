package com.example.todolist.domain.model.category;

import com.example.todolist.domain.model.Title;
import com.example.todolist.domain.model.UserId;

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
    private final CategoryId id;
    private Title title;
    private final UserId userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    // Полный конструктор (для восстановления из БД)
    public Category(CategoryId id, Title title, UserId userId, LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    // Бизнес-методы

    /**
     * Обновить название категории
     */
    public Category updateTitle(Title newTitle) {
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
        return Objects.equals(this.userId.getValue(), userId);
    }

    // Getters
    public CategoryId getId() {
        return id;
    }

    public Title getTitle() {
        return title;
    }

    public UserId getUserId() {
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
                ", title='" + title.getValue() + '\'' +
                ", userId=" + userId.getValue() +
                ", deleted=" + isDeleted() +
                '}';
    }

    public void renameTitle(Title title) {
        this.title = title;
    }
}
