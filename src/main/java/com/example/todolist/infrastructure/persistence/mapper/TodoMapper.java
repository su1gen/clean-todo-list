package com.example.todolist.infrastructure.persistence.mapper;

import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.infrastructure.persistence.entity.TodoEntity;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    /**
     * Доменная модель → JPA Entity
     */
    public TodoEntity toEntity(Todo todo) {
        TodoEntity entity = new TodoEntity(
                todo.getTitle(),
                todo.getDescription(),
                todo.getCategoryId(),
                todo.getUserId(),
                todo.getStatus(),
                todo.getPlannedAt()
        );

        if (todo.getId() != null) {
            entity.setId(todo.getId());
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
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getCategoryId(),
                entity.getUserId(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getDeletedAt(),
                entity.getPlannedAt()
        );
    }
}
