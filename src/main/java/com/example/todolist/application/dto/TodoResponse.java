package com.example.todolist.application.dto;

import java.time.LocalDateTime;

public record TodoResponse(
        Long id,
        String title,
        String description,
        Long categoryId,
        TodoStatusResponse status,
        LocalDateTime createdAt,
        LocalDateTime plannedAt
) {
}
