package com.example.todolist.domain.repository;

import com.example.todolist.domain.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Порт (интерфейс) для работы с хранилищем категорий.
 * Оперирует доменными моделями.
 */
public interface CategoryRepository {

    /**
     * Сохранить категорию (создать или обновить)
     */
    Category save(Category category);

    /**
     * Получить все категории пользователя (без удалённых)
     */
    List<Category> findByUserIdAndNotDeletedWithDescOrder(Long userId);

    /**
     * Найти категорию по ID только если не удалена
     */
    Optional<Category> findByIdAndNotDeleted(Long id);

    /**
     * Найти категорию по ID только если не удалена
     */

    List<Category> findByIdsAndDeletedAtIsNull(Set<Long> ids);

    /**
     * получить следующий ID
     */
    Long getNextCategoryId();
}
