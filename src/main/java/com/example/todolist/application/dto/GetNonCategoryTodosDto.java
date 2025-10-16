package com.example.todolist.application.dto;

public record GetNonCategoryTodosDto(
        String status,
        Long userId
) {
}
