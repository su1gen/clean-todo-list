package com.example.todolist.application.inbound.todo;

import com.example.todolist.application.dto.TodoResponse;

import java.util.List;

public interface GetNonCategoryTodos {
    List<TodoResponse> execute(Long userId, String status);
}
