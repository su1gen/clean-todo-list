package com.example.todolist.application.dto;

import com.example.todolist.domain.model.todo.Todo;
import com.example.todolist.domain.model.todo.TodoStatus;

import java.time.LocalDateTime;

public record TodoWithCategoryDto(
        Long id,
        String title,
        String description,
        String categoryTitle,
        TodoStatus status,
        LocalDateTime createdAt,
        LocalDateTime plannedAt
) {
    public static TodoWithCategoryDto from(Todo todo) {
        return new TodoWithCategoryDto(
                todo.getId().getValue(),
                todo.getTitle().getValue(),
                todo.getDescription(),
                todo.getCategoryTitle().getValue(),
                todo.getStatus(),
                todo.getCreatedAt(),
                todo.getPlannedAt()
        );
    }
}
