package com.example.todolist.application.outbound.category;

import com.example.todolist.domain.model.category.CategoryId;

public interface CategoryNextIdExtractor {
    /**
     * получить следующий ID
     */
    CategoryId getNextCategoryId();
}
