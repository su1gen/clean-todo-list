package com.example.todolist.presentation.mapper;

import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.domain.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryResponseMapper {
    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId().getValue(),
                category.getTitle().getValue()
        );
    }
}
