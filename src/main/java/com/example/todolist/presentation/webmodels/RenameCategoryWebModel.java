package com.example.todolist.presentation.webmodels;


import com.example.todolist.application.dto.category.RenameCategoryDto;

public record RenameCategoryWebModel(
        String title
) {
    public RenameCategoryDto toDto(Long categoryId, Long userId) {
        return new RenameCategoryDto(
                categoryId,
                userId,
                title
        );
    }
}
