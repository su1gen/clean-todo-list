package com.example.todolist.application.usecase;

import com.example.todolist.domain.exception.CategoryAccessDeniedException;
import com.example.todolist.domain.exception.CategoryNotFoundException;
import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.repository.CategoryRepository;
import jakarta.inject.Named;

/**
 * Use Case: Удаление категории (soft delete).
 * <p>
 * Бизнес-правила:
 * 1. Категория должна существовать
 * 2. Пользователь может удалять только свои категории
 * 3. Soft delete (устанавливается deletedAt)
 */
@Named
class DeleteCategoryUseCase implements DeleteCategory{

    private final CategoryRepository categoryRepository;

     DeleteCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Soft delete категории
     */
    @Override
    public void execute(Long categoryId, Long userId) {
        // 1. Найти категорию (только не удалённые)
        Category category = categoryRepository.findByIdAndNotDeleted(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        // 2. Проверить права доступа
        if (!category.belongsToUser(userId)) {
            throw new CategoryAccessDeniedException(categoryId, userId);
        }

        // 3. Пометить как удалённую через бизнес-метод
        Category deletedCategory = category.markAsDeleted();

        // 4. Сохранить
        categoryRepository.save(deletedCategory);
    }

    /**
     * Восстановить удалённую категорию
     */
    @Override
    public void restore(Long categoryId, Long userId) {
        // 1. Найти категорию (включая удалённые)
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        // 2. Проверить права доступа
        if (!category.belongsToUser(userId)) {
            throw new CategoryAccessDeniedException(categoryId, userId);
        }

        // 3. Восстановить через бизнес-метод
        Category restoredCategory = category.restore();

        // 4. Сохранить
        categoryRepository.save(restoredCategory);
    }
}