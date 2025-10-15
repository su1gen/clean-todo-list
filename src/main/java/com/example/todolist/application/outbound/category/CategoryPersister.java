package com.example.todolist.application.outbound.category;

import com.example.todolist.domain.model.Category;

public interface CategoryPersister {
    /**
     * Сохранить категорию (создать или обновить)
     */
    Category persist(Category category);
}
