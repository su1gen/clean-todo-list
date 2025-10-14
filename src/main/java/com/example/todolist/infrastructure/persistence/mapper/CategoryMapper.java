package com.example.todolist.infrastructure.persistence.mapper;

import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.model.CategoryId;
import com.example.todolist.domain.model.Title;
import com.example.todolist.domain.model.UserId;
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
    public Category toDomain(CategoryEntity entity) {
        return new Category(
                CategoryId.of(entity.getId()),
                Title.of(entity.getTitle()),
                UserId.of(entity.getUserId()),
                entity.getCreatedAt(),
                entity.getDeletedAt()
        );
    }
}
