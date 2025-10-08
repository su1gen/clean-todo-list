package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.application.dto.UpdateCategoryRequest;

public interface UpdateCategory {
    CategoryResponse execute(Long categoryId, UpdateCategoryRequest request, Long userId);
}
