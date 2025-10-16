package com.example.todolist.application.dto;

import java.time.LocalDateTime;

public record CreateTodoDto(
        String title,
        String description,
        Long categoryId,
        Long userId,
        LocalDateTime plannedAt
) {
}
