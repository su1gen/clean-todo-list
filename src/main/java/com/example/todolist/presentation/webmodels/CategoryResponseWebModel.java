package com.example.todolist.presentation.webmodels;

import com.example.todolist.domain.model.Category;

public record CategoryResponseWebModel(
        Long id,
        String title
) {
    public static CategoryResponseWebModel from(Category category) {
        return new CategoryResponseWebModel(
                category.getId().getValue(),
                category.getTitle().getValue()
        );
    }
}
