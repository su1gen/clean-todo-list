package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.domain.model.Category;

import java.util.List;

public interface GetUserCategories {
    List<CategoryResponse> execute(Long userId);

    List<CategoryResponse> executeIncludingDeleted(Long userId);

    CategoryResponse mapToResponse(Category category);
}
