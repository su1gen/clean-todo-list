package com.example.todolist.infrastructure.persistence.entity;


import com.example.todolist.domain.model.category.Category;
import com.example.todolist.domain.model.category.CategoryId;
import com.example.todolist.domain.model.Title;
import com.example.todolist.domain.model.UserId;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    private Long id;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // JPA требует пустой конструктор
    public CategoryEntity() {}

    public CategoryEntity(String title, Long userId) {
        this.title = title;
        this.userId = userId;
    }

    /**
     * Доменная модель → JPA Entity
     */
    public static CategoryEntity fromDomain(Category category) {
        CategoryEntity entity = new CategoryEntity(
                category.getTitle().getValue(),
                category.getUserId().getValue()
        );

        if (category.getId().notEmpty()) {
            entity.setId(category.getId().getValue());
        }

        if (category.getCreatedAt() != null) {
            entity.setCreatedAt(category.getCreatedAt());
        }

        entity.setDeletedAt(category.getDeletedAt());

        return entity;
    }

    /**
     * JPA Entity → Доменная модель
     */
    public static Category toDomain(CategoryEntity entity) {
        return new Category(
                CategoryId.of(entity.getId()),
                Title.of(entity.getTitle()),
                UserId.of(entity.getUserId()),
                entity.getCreatedAt(),
                entity.getDeletedAt()
        );
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
}
