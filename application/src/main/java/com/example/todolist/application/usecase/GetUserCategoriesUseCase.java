package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.repository.CategoryRepository;
import jakarta.inject.Named;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Use Case: Получение всех категорий пользователя.
 * <p>
 * Бизнес-правило:
 * - Возвращаем только не удалённые категории
 */
@Named
class GetUserCategoriesUseCase implements GetUserCategories {

    private final CategoryRepository categoryRepository;

    GetUserCategoriesUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Получить все категории пользователя (без удалённых)
     */
    @Override
    public List<CategoryResponse> execute(Long userId) {
        List<Category> categories = categoryRepository.findByUserIdAndNotDeleted(userId);

        return categories.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Получить все категории пользователя (включая удалённые)
     */
    @Override
    public List<CategoryResponse> executeIncludingDeleted(Long userId) {
        List<Category> categories = categoryRepository.findByUserId(userId);

        return categories.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getTitle(),
                category.getUserId(),
                category.getCreatedAt(),
                category.isDeleted()
        );
    }
}
