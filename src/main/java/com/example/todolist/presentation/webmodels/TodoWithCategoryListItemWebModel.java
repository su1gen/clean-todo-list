package com.example.todolist.presentation.webmodels;

import com.example.todolist.domain.model.todo.Todo;

public record TodoWithCategoryListItemWebModel(
        Long id,
        String title,
        String statusTitle,
        String categoryTitle
) {
    public static TodoWithCategoryListItemWebModel from(Todo todo) {
        return new TodoWithCategoryListItemWebModel(
                todo.getId().getValue(),
                todo.getTitle().getValue(),
                todo.getStatus().getTitle(),
                todo.getCategoryTitle().getValue()
        );
    }
}
