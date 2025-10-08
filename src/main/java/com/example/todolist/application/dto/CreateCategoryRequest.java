package com.example.todolist.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(
        @NotBlank(message = "Название категории не может быть пустым")
        @Size(max = 255, message = "Название не может быть длиннее 255 символов")
        String title
) {
}
