package com.example.todolist.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Доменная модель задачи (Todo).
 *
 * Бизнес-правила:
 * - Todo принадлежит пользователю (userId)
 * - Todo может быть привязана к категории (categoryId - опционально)
 * - Title обязателен, description обязателен
 * - Статус контролирует жизненный цикл задачи
 * - plannedAt - дата планирования (опционально)
 * - Soft delete через deletedAt
 */
public class Todo {
    private final Long id;
    private final String title;
    private final String description;
    private final Long categoryId;
    private final Long userId;
    private final TodoStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;
    private final LocalDateTime plannedAt;

    public Todo(
            String title,
            String description,
            Long categoryId,
            Long userId,
            TodoStatus status,
            LocalDateTime plannedAt
    ) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.userId = userId;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.deletedAt = null;
        this.plannedAt = plannedAt;
    }

    public Todo(
            Long id,
            String title,
            String description,
            Long categoryId,
            Long userId,
            TodoStatus status,
            LocalDateTime createdAt,
            LocalDateTime deletedAt,
            LocalDateTime plannedAt
    ) {
        this.id = id;
        this.title = validateTitle(title);
        this.description = validateDescription(description);
        this.categoryId = categoryId;
        this.userId = validateUserId(userId);
        this.status = status;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.plannedAt = plannedAt;
    }

    // Валидация title
    private String validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Todo title cannot be empty");
        }
        if (title.length() > 255) {
            throw new IllegalArgumentException("Todo title cannot exceed 255 characters");
        }
        return title.trim();
    }

    // Валидация description
    private String validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Todo description cannot be empty");
        }
        return description.trim();
    }

    // Валидация userId
    private Long validateUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Todo must belong to a user");
        }
        return userId;
    }

    /**
     * Обновить основные данные задачи
     */
    public Todo update(String newTitle, String newDescription, Long newCategoryId, LocalDateTime newPlannedAt) {
        return new Todo(this.id, newTitle, newDescription, newCategoryId, this.userId,
                this.status, this.createdAt, this.deletedAt, newPlannedAt);
    }

    /**
     * Изменить статус задачи с валидацией перехода
     */
    public Todo changeStatus(TodoStatus newStatus) {
        if (!this.status.canTransitionTo(newStatus)) {
            throw new IllegalStateException(
                    "Cannot transition from " + this.status + " to " + newStatus
            );
        }
        return new Todo(this.id, this.title, this.description, this.categoryId, this.userId,
                newStatus, this.createdAt, this.deletedAt, this.plannedAt);
    }

    /**
     * Привязать к категории
     */
    public Todo assignToCategory(Long categoryId) {
        return new Todo(this.id, this.title, this.description, categoryId, this.userId,
                this.status, this.createdAt, this.deletedAt, this.plannedAt);
    }

    /**
     * Открепить от категории
     */
    public Todo removeFromCategory() {
        return new Todo(this.id, this.title, this.description, null, this.userId,
                this.status, this.createdAt, this.deletedAt, this.plannedAt);
    }

    /**
     * Пометить как удалённую (soft delete)
     */
    public Todo markAsDeleted() {
        if (this.deletedAt != null) {
            throw new IllegalStateException("Todo is already deleted");
        }
        return new Todo(this.id, this.title, this.description, this.categoryId, this.userId,
                this.status, this.createdAt, LocalDateTime.now(), this.plannedAt);
    }

    /**
     * Восстановить удалённую задачу
     */
    public Todo restore() {
        if (this.deletedAt == null) {
            throw new IllegalStateException("Todo is not deleted");
        }
        return new Todo(this.id, this.title, this.description, this.categoryId, this.userId,
                this.status, this.createdAt, null, this.plannedAt);
    }

    /**
     * Проверка: удалена ли задача
     */
    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    public boolean isOverdue() {
        return this.plannedAt != null
                && this.plannedAt.isBefore(LocalDateTime.now());
    }

    public boolean belongsToUser(Long userId) {
        return Objects.equals(this.userId, userId);
    }


    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Long getCategoryId() { return categoryId; }
    public Long getUserId() { return userId; }
    public TodoStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public LocalDateTime getPlannedAt() { return plannedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equals(id, todo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
