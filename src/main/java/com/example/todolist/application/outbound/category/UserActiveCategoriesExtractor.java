package com.example.todolist.application.outbound.category;

import com.example.todolist.domain.model.Category;

import java.util.List;

public interface UserActiveCategoriesExtractor {
    /**
     * Получить все категории пользователя (без удалённых)
     */
    List<Category> getUserActiveCategories(Long userId);
}
