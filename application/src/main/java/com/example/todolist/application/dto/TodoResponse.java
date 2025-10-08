package com.example.todolist.application.dto;

import java.time.LocalDateTime;

public record TodoResponse(
        Long id,
        String title,
        String description,
        Long categoryId,
        Long userId,
        String status,
        String statusTitle,
        LocalDateTime createdAt,
        LocalDateTime plannedAt,
        boolean deleted,
        boolean overdue
) {
}
