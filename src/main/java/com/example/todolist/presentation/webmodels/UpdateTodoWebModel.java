package com.example.todolist.presentation.webmodels;


import com.example.todolist.application.dto.UpdateTodoDto;

import java.time.LocalDateTime;

public record UpdateTodoWebModel(
        String title,
        String description,
        Long categoryId,
        Integer statusId,
        LocalDateTime plannedAt
) {
    public UpdateTodoDto toDto(Long todoId, Long userId) {
        return new UpdateTodoDto(
                title,
                description,
                todoId,
                categoryId,
                userId,
                statusId,
                plannedAt
        );
    }
}
