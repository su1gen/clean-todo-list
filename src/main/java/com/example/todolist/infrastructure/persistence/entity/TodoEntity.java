package com.example.todolist.infrastructure.persistence.entity;

import com.example.todolist.domain.model.*;
import com.example.todolist.domain.model.category.CategoryId;
import com.example.todolist.domain.model.todo.Todo;
import com.example.todolist.domain.model.todo.TodoCategoryTitle;
import com.example.todolist.domain.model.todo.TodoId;
import com.example.todolist.domain.model.todo.TodoStatus;
import com.example.todolist.domain.model.user.UserId;
import com.example.todolist.infrastructure.persistence.converter.TodoStatusConverter;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
public class TodoEntity {
    @Id
    private Long id;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_title")
    private String categoryTitle;

    @Column(name = "user_id")
    private Long userId;

    @Convert(converter = TodoStatusConverter.class)
    private TodoStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;
    private LocalDateTime plannedAt;


    public TodoEntity() {}

    public TodoEntity(
            String title,
            String description,
            Long categoryId,
            String categoryTitle,
            Long userId,
            TodoStatus status,
            LocalDateTime plannedAt
    ) {
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.userId = userId;
        this.status = status;
        this.plannedAt = plannedAt;
    }

    /**
     * Доменная модель → JPA Entity
     */
    public static TodoEntity fromDomain(Todo todo) {
        TodoEntity entity = new TodoEntity(
                todo.getTitle().getValue(),
                todo.getDescription(),
                todo.getCategoryId().getValue(),
                todo.getCategoryTitle().getValue(),
                todo.getUserId().getValue(),
                todo.getStatus(),
                todo.getPlannedAt()
        );

        if (todo.getId().notEmpty()) {
            entity.setId(todo.getId().getValue());
        }

        if (todo.getCreatedAt() != null) {
            entity.setCreatedAt(todo.getCreatedAt());
        }

        entity.setDeletedAt(todo.getDeletedAt());

        return entity;
    }

    /**
     * JPA Entity → Доменная модель
     */
    public static Todo toDomain(TodoEntity entity) {
        return new Todo(
                TodoId.of(entity.getId()),
                Title.of(entity.getTitle()),
                entity.getDescription(),
                CategoryId.of(entity.getCategoryId()),
                TodoCategoryTitle.of(entity.getCategoryTitle()),
                UserId.of(entity.getUserId()),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getDeletedAt(),
                entity.getPlannedAt());
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }
    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TodoStatus getStatus() {
        return status;
    }
    public void setStatus(TodoStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getPlannedAt() {
        return plannedAt;
    }
    public void setPlannedAt(LocalDateTime plannedAt) {
        this.plannedAt = plannedAt;
    }
}
