package com.example.todolist.presentation.webmodels;

import com.example.todolist.domain.model.Todo;

public record TodoListItemWebModel(
        Long id,
        String title,
        String statusTitle
) {
    public static TodoListItemWebModel from(Todo todo) {
        return new TodoListItemWebModel(
                todo.getId().getValue(),
                todo.getTitle().getValue(),
                todo.getStatus().getTitle()
        );
    }
}
