package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.TodoResponse;

import java.util.List;

public interface GetUserTodos {
    List<TodoResponse> execute(Long userId);

    List<TodoResponse> executeByStatus(Long userId, String statusUrlParam);

    List<TodoResponse> executeByCategory(Long userId, Long categoryId);

    List<TodoResponse> executeOverdue(Long userId);

    List<TodoResponse> executeToday(Long userId);
}
