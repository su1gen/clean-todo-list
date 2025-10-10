package com.example.todolist.application.dto;

import java.util.List;

public record CategoryWithTodosResponse(
        String title,
        List<TodoResponse> todos
) {
}
