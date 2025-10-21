package com.example.todolist.application.dto.category;

public record RenameCategoryDto(
        Long categoryId,
        Long userId,
        String title
) {
}
