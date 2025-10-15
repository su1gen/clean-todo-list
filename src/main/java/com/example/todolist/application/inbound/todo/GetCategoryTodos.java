package com.example.todolist.application.inbound.todo;

import com.example.todolist.application.dto.CategoryWithTodosResponse;

public interface GetCategoryTodos {
    CategoryWithTodosResponse execute(Long userId, Long categoryId, String status);
}
