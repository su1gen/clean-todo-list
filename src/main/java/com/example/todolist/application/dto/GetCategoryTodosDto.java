package com.example.todolist.application.dto;

public record GetCategoryTodosDto(
        String status,
        Long categoryId,
        Long userId
) {
}
