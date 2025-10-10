package com.example.todolist.application.dto;

import java.time.LocalDateTime;

public record TodoWithCategoryResponse(
        Long id,
        String title,
        String description,
        CategoryResponse category,
        String statusTitle,
        LocalDateTime createdAt,
        LocalDateTime plannedAt
) {
}
