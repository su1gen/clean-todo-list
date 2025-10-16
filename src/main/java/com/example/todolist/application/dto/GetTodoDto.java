package com.example.todolist.application.dto;

public record GetTodoDto(
        Long todoId,
        Long userId
) {
}
