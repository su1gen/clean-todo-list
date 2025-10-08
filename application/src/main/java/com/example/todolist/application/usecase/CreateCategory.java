package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.application.dto.CreateCategoryDto;

public interface CreateCategory {
    CategoryResponse execute(CreateCategoryDto request, Long userId);
}
