package com.example.todolist.application.outbound.category;

import com.example.todolist.domain.model.category.Category;

import java.util.List;
import java.util.Set;

public interface CategoriesExtractor {
    /**
     * Найти категории по ID только если не удалены
     */

    List<Category> getCategoriesByIds(Set<Long> ids);
}
