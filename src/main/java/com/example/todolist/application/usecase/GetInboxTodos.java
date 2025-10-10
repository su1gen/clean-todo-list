package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.TodoWithCategoryResponse;

import java.util.List;

public interface GetInboxTodos {
    List<TodoWithCategoryResponse> execute(Long userId);
}
