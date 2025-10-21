package com.example.todolist.application.inbound.category;

import com.example.todolist.application.dto.CategoryWithTodosDto;
import com.example.todolist.application.dto.GetCategoryTodosDto;

public interface GetCategoryTodos {
    CategoryWithTodosDto execute(GetCategoryTodosDto getCategoryTodosDto);
}
