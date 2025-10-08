package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.application.dto.UpdateCategoryRequest;
import com.example.todolist.domain.exception.CategoryAccessDeniedException;
import com.example.todolist.domain.exception.CategoryNotFoundException;
import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * Use Case: Обновление категории.
 *
 * Бизнес-правила:
 * 1. Категория должна существовать
 * 2. Пользователь может изменять только свои категории
 * 3. Нельзя изменить удалённую категорию
 */
@Service
@Transactional
public class UpdateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    public UpdateCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponse execute(Long categoryId, UpdateCategoryRequest request, Long userId) {
        // 1. Найти категорию
        Category category = categoryRepository.findByIdAndNotDeleted(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        // 2. Проверить права доступа
        if (!category.belongsToUser(userId)) {
            throw new CategoryAccessDeniedException(categoryId, userId);
        }

        // 3. Обновить через бизнес-метод
        Category updatedCategory = category.updateTitle(request.title());

        // 4. Сохранить
        Category savedCategory = categoryRepository.save(updatedCategory);

        // 5. Вернуть ответ
        return mapToResponse(savedCategory);
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
