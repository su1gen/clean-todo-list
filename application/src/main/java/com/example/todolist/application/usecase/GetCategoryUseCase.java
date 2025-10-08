package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.domain.exception.CategoryAccessDeniedException;
import com.example.todolist.domain.exception.CategoryNotFoundException;
import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.repository.CategoryRepository;
import jakarta.inject.Named;

/**
 * Use Case: Получение категории по ID.
 * <p>
 * Бизнес-правила:
 * 1. Категория должна существовать
 * 2. Пользователь может видеть только свои категории
 */
@Named
class GetCategoryUseCase implements GetCategory{

    private final CategoryRepository categoryRepository;

     GetCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Получить категорию по ID с проверкой прав доступа
     */
    @Override
    public CategoryResponse execute(Long categoryId, Long userId) {
        // 1. Найти категорию (включая удалённые)
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        // 2. Проверить права доступа
        if (!category.belongsToUser(userId)) {
            throw new CategoryAccessDeniedException(categoryId, userId);
        }

        // 3. Преобразовать в DTO
        return mapToResponse(category);
    }

    /**
     * Получить не удалённую категорию
     */
    @Override
    public CategoryResponse executeNotDeleted(Long categoryId, Long userId) {
        Category category = categoryRepository.findByIdAndNotDeleted(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        if (!category.belongsToUser(userId)) {
            throw new CategoryAccessDeniedException(categoryId, userId);
        }

        return mapToResponse(category);
    }

    private CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getTitle(),
                category.getUserId(),
                category.getCreatedAt(),
                category.isDeleted()
        );
    }
}
