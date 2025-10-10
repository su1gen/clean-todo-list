package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryResponse;

import java.util.List;

public interface GetCategories {
    List<CategoryResponse> execute(Long userId);
}
