package com.example.todolist.presentation.mapper;

import com.example.todolist.presentation.webmodels.CategoryResponseWebModel;
import com.example.todolist.domain.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryResponseMapper {
    public CategoryResponseWebModel toResponse(Category category) {
        return new CategoryResponseWebModel(
                category.getId().getValue(),
                category.getTitle().getValue()
        );
    }
}
