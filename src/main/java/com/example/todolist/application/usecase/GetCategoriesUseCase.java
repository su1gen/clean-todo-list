package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class GetCategoriesUseCase implements GetCategories{
    private final CategoryRepository categoryRepository;

    public GetCategoriesUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponse> execute(Long userId) {
        List<Category> categories = categoryRepository.findByUserIdAndNotDeletedWithDescOrder(userId);

        return categories
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getTitle()
        );
    }
}
