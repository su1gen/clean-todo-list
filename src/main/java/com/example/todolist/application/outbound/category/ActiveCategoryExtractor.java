package com.example.todolist.application.outbound.category;

import com.example.todolist.domain.model.category.Category;
import com.example.todolist.domain.model.category.CategoryId;

import java.util.Optional;

public interface ActiveCategoryExtractor {
    /**
     * Найти категорию по ID только если не удалена
     */
    Optional<Category> getActiveCategoryById(CategoryId categoryId);
}
