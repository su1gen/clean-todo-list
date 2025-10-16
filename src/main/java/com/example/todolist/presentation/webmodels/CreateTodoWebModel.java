package com.example.todolist.presentation.webmodels;


import com.example.todolist.application.dto.CreateTodoDto;

import java.time.LocalDateTime;

public record CreateTodoWebModel(
        String title,
        String description,
        Long categoryId,
        LocalDateTime plannedAt
) {
    public CreateTodoDto toDto(Long userId){
        return new CreateTodoDto(
                title,
                description,
                categoryId,
                userId,
                plannedAt
        );
    }
}
