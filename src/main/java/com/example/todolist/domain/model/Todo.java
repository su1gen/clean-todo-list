package com.example.todolist.domain.model;

import com.example.todolist.domain.model.category.CategoryId;
import com.example.todolist.domain.model.category.TodoCategoryTitle;

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
    private final TodoId id;
    private final Title title;
    private final String description;
    private final CategoryId categoryId;
    private final TodoCategoryTitle categoryTitle;
    private final UserId userId;
    private final TodoStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;
    private final LocalDateTime plannedAt;

    public Todo(
            TodoId id,
            Title title,
            String description,
            CategoryId categoryId,
            TodoCategoryTitle categoryTitle,
            UserId userId,
            TodoStatus status,
            LocalDateTime createdAt,
            LocalDateTime deletedAt,
            LocalDateTime plannedAt
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.plannedAt = plannedAt;
    }

    /**
     * Обновить основные данные задачи
     */
    public Todo update(Title newTitle, String newDescription, CategoryId newCategoryId, TodoCategoryTitle categoryTitle, TodoStatus newTodoStatus, LocalDateTime newPlannedAt) {
        return new Todo(this.id, newTitle, newDescription, newCategoryId, categoryTitle,
                this.userId, newTodoStatus, this.createdAt, this.deletedAt, newPlannedAt);
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
        return new Todo(this.id, this.title, this.description, this.categoryId, this.categoryTitle,
                this.userId, newStatus, this.createdAt, this.deletedAt, this.plannedAt);
    }

    /**
     * Пометить как удалённую (soft delete)
     */
    public Todo markAsDeleted() {
        if (this.deletedAt != null) {
            throw new IllegalStateException("Todo is already deleted");
        }
        return new Todo(this.id, this.title, this.description, this.categoryId, this.categoryTitle,
                this.userId, this.status, this.createdAt, LocalDateTime.now(), this.plannedAt);
    }

    /**
     * Восстановить удалённую задачу
     */
    public Todo restore() {
        if (this.deletedAt == null) {
            throw new IllegalStateException("Todo is not deleted");
        }
        return new Todo(this.id, this.title, this.description, this.categoryId, this.categoryTitle,
                this.userId, this.status, this.createdAt, null, this.plannedAt);
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
        return Objects.equals(this.userId.getValue(), userId);
    }

    public boolean hasCategory() {
        return Objects.nonNull(categoryId);
    }


    // Getters
    public TodoId getId() { return id; }
    public Title getTitle() { return title; }
    public String getDescription() { return description; }
    public CategoryId getCategoryId() { return categoryId; }
    public TodoCategoryTitle getCategoryTitle() { return categoryTitle; }
    public UserId getUserId() { return userId; }
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
