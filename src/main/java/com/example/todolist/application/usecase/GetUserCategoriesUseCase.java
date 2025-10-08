package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Use Case: Получение всех категорий пользователя.
 *
 * Бизнес-правило:
 * - Возвращаем только не удалённые категории
 */
@Service
@Transactional
public class GetUserCategoriesUseCase {

    private final CategoryRepository categoryRepository;

    public GetUserCategoriesUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Получить все категории пользователя (без удалённых)
     */
    public List<CategoryResponse> execute(Long userId) {
        List<Category> categories = categoryRepository.findByUserIdAndNotDeleted(userId);

        return categories.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Получить все категории пользователя (включая удалённые)
     */
    public List<CategoryResponse> executeIncludingDeleted(Long userId) {
        List<Category> categories = categoryRepository.findByUserId(userId);

        return categories.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
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
