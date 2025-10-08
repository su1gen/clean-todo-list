package com.example.todolist.application.dto;

import java.time.LocalDateTime;

public record UpdateTodoRequest(
//        @NotBlank(message = "Название задачи не может быть пустым")
//        @Size(max = 255, message = "Название не может быть длиннее 255 символов")
        String title,

//        @NotBlank(message = "Описание задачи не может быть пустым")
        String description,

        Long categoryId,
        Integer statusId,
        LocalDateTime plannedAt
) {
}
