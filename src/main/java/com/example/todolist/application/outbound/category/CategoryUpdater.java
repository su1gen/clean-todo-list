package com.example.todolist.application.outbound.category;

import com.example.todolist.domain.model.Category;

public interface CategoryUpdater {
    /**
     * Сохранить категорию (создать или обновить)
     */
    Category update(Category category);
}
