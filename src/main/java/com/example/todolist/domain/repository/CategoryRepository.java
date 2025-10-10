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

//    /**
//     * Найти категорию по ID (включая удалённые)
//     */
//    Optional<Category> findById(Long id);
//

//

//
//    /**
//     * Получить все категории пользователя (включая удалённые)
//     */
//    List<Category> findByUserId(Long userId);
//
//    /**
//     * Проверить существование категории по ID
//     */
//    boolean existsById(Long id);
//
//    /**
//     * Физическое удаление категории (для тестов или очистки)
//     */
//    void deleteById(Long id);
}
