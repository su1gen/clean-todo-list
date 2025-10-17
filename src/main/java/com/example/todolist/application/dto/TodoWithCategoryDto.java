package com.example.todolist.application.dto;

import com.example.todolist.domain.model.TodoStatus;

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
}
