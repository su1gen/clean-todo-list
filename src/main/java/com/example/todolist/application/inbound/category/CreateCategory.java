package com.example.todolist.application.inbound.category;

import com.example.todolist.application.dto.CreateCategoryDto;
import com.example.todolist.domain.model.category.Category;

public interface CreateCategory {
    Category execute(CreateCategoryDto createCategoryDto);
}
