package com.example.todolist.presentation.webmodels;

import com.example.todolist.domain.model.Todo;

import java.time.LocalDateTime;

public record TodoResponseWebModel(
        Long id,
        String title,
        String description,
        Long categoryId,
        TodoStatusResponseWebModel status,
        LocalDateTime createdAt,
        LocalDateTime plannedAt
) {
    public static TodoResponseWebModel from(Todo todo) {
        return new TodoResponseWebModel(
                todo.getId().getValue(),
                todo.getTitle().getValue(),
                todo.getDescription(),
                todo.getCategoryId().getValue(),
                new TodoStatusResponseWebModel(
                        todo.getStatus().getId(),
                        todo.getStatus().getTitle()
                ),
                todo.getCreatedAt(),
                todo.getPlannedAt()
        );
    }
}
