package com.example.todolist.application.outbound.category;

import com.example.todolist.domain.model.category.CategoryId;

public interface CategoryExister {
    /**
     * Проверить есть ли категория по Id
     */
    boolean exists(CategoryId categoryId);
}
