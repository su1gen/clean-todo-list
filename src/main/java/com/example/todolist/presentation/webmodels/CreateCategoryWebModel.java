package com.example.todolist.presentation.webmodels;


import com.example.todolist.application.dto.CreateCategoryDto;

public record CreateCategoryWebModel(
        String title
) {
    public CreateCategoryDto toDto(Long userId) {
        return new CreateCategoryDto(
                title,
                userId
        );
    }
}
