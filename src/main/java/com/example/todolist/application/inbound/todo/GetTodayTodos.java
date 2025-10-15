package com.example.todolist.application.inbound.todo;

import com.example.todolist.application.dto.TodoWithCategoryResponse;

import java.util.List;

public interface GetTodayTodos {
    List<TodoWithCategoryResponse> execute(Long userId);
}
