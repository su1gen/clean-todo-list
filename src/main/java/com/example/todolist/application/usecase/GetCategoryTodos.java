package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryWithTodosResponse;

import java.util.List;

public interface GetCategoryTodos {
    CategoryWithTodosResponse execute(Long userId, Long categoryId, String status);
}
