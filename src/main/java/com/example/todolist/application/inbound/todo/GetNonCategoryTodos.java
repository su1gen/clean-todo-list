package com.example.todolist.application.inbound.todo;

import com.example.todolist.application.dto.GetNonCategoryTodosDto;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.presentation.webmodels.TodoResponseWebModel;

import java.util.List;

public interface GetNonCategoryTodos {
    List<Todo> execute(GetNonCategoryTodosDto  getNonCategoryTodosDto);
}
