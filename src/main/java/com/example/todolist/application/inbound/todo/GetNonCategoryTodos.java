package com.example.todolist.application.inbound.todo;

import com.example.todolist.application.dto.GetNonCategoryTodosDto;
import com.example.todolist.domain.model.todo.Todo;

import java.util.List;

public interface GetNonCategoryTodos {
    List<Todo> execute(GetNonCategoryTodosDto  getNonCategoryTodosDto);
}
