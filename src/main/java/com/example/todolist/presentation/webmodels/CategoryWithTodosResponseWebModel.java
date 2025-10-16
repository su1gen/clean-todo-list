package com.example.todolist.presentation.webmodels;

import com.example.todolist.application.dto.CategoryWithTodosDto;

import java.util.List;

public record CategoryWithTodosResponseWebModel(
        String title,
        List<TodoResponseWebModel> todos
) {
    public static  CategoryWithTodosResponseWebModel from(CategoryWithTodosDto categoryWithTodosDto) {
        return new CategoryWithTodosResponseWebModel(
                categoryWithTodosDto.category().getTitle().getValue(),
                categoryWithTodosDto.todos().stream()
                        .map(TodoResponseWebModel::from)
                        .toList()
        );
    }
}
