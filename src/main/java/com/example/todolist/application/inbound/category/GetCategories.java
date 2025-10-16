package com.example.todolist.application.inbound.category;

import com.example.todolist.domain.model.Category;

import java.util.List;

public interface GetCategories {
    List<Category> execute(Long userId);
}
