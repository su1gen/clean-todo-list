package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryResponse;

public interface GetCategory {
    CategoryResponse execute(Long categoryId, Long userId);

    CategoryResponse executeNotDeleted(Long categoryId, Long userId);
}
