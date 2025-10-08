package com.example.todolist.application.dto;

import java.time.LocalDateTime;

public record CategoryResponse(
        Long id,
        String title,
        Long userId,
        LocalDateTime createdAt,
        boolean deleted
) {
}
