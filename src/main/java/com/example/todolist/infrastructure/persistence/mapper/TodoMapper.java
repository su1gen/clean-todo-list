package com.example.todolist.infrastructure.persistence.mapper;

import com.example.todolist.domain.model.*;
import com.example.todolist.infrastructure.persistence.entity.TodoEntity;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    /**
     * Доменная модель → JPA Entity
     */
    public TodoEntity toEntity(Todo todo) {
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
    public Todo toDomain(TodoEntity entity) {
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
}
