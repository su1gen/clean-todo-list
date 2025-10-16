package com.example.todolist.application.dto;


import java.time.LocalDateTime;

public record UpdateTodoDto(
        String title,
        String description,
        Long todoId,
        Long categoryId,
        Long userId,
        Integer statusId,
        LocalDateTime plannedAt
) {
}
