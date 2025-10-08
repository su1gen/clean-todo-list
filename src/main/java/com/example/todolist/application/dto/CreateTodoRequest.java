package com.example.todolist.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateTodoRequest(
        @NotBlank(message = "Название задачи не может быть пустым")
        @Size(max = 255, message = "Название не может быть длиннее 255 символов")
        String title,

        @NotBlank(message = "Описание задачи не может быть пустым")
        String description,

        Long categoryId,
        LocalDateTime plannedAt
) {
}
