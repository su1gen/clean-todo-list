package com.example.todolist.application.dto;

import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.model.Todo;

import java.util.List;

public record CategoryWithTodosDto(
        Category category,
        List<Todo> todos
) {

}
