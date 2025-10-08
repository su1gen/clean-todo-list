package com.example.todolist.infrastructure.persistence.mapper;

import com.example.todolist.domain.model.Category;
import com.example.todolist.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper между доменной моделью Category и JPA entity.
 */
@Component
public class CategoryMapper {

    /**
     * Доменная модель → JPA Entity
     */
    public CategoryEntity toEntity(Category category) {
        CategoryEntity entity = new CategoryEntity(
                category.getTitle(),
                category.getUserId()
        );

        if (category.getId() != null) {
            entity.setId(category.getId());
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
    public Category toDomain(CategoryEntity entity) {
        return new Category(
                entity.getId(),
                entity.getTitle(),
                entity.getUserId(),
                entity.getCreatedAt(),
                entity.getDeletedAt()
        );
    }
}
