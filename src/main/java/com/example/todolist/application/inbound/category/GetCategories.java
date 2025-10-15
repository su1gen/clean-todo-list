package com.example.todolist.application.inbound.category;

import com.example.todolist.application.dto.CategoryResponse;

import java.util.List;

public interface GetCategories {
    List<CategoryResponse> execute(Long userId);
}
