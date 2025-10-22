package com.example.todolist.application.inbound.category;

import com.example.todolist.application.dto.GetCategoryTodosDto;
import com.example.todolist.domain.model.todo.Todo;

import java.util.List;

public interface GetCategoryTodos {
    List<Todo> execute(GetCategoryTodosDto getCategoryTodosDto);
}
