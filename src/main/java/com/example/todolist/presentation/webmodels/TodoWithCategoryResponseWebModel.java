package com.example.todolist.presentation.webmodels;

import com.example.todolist.application.dto.TodoWithCategoryDto;

import java.time.LocalDateTime;

public record TodoWithCategoryResponseWebModel(
        Long id,
        String title,
        String description,
        CategoryResponseWebModel category,
        String statusTitle,
        LocalDateTime createdAt,
        LocalDateTime plannedAt
) {
    public static TodoWithCategoryResponseWebModel from(TodoWithCategoryDto todoWithCategoryDto) {
        return new TodoWithCategoryResponseWebModel(
                todoWithCategoryDto.id(),
                todoWithCategoryDto.title(),
                todoWithCategoryDto.description(),
                CategoryResponseWebModel.from(todoWithCategoryDto.category()),
                todoWithCategoryDto.status().getTitle(),
                todoWithCategoryDto.createdAt(),
                todoWithCategoryDto.plannedAt()
        );
    }
}
