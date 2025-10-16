package com.example.todolist.application.dto;

public record CreateCategoryDto(
        String title,
        Long userId
) {
}
