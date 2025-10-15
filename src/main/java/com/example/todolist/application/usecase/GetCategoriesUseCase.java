package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.application.inbound.category.GetCategories;
import com.example.todolist.application.outbound.category.UserActiveCategoriesExtractor;
import com.example.todolist.domain.model.Category;
import com.example.todolist.presentation.mapper.CategoryResponseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class GetCategoriesUseCase implements GetCategories {
    private final UserActiveCategoriesExtractor userActiveCategoriesExtractor;
    private final CategoryResponseMapper categoryResponseMapper;

    public GetCategoriesUseCase(UserActiveCategoriesExtractor userActiveCategoriesExtractor, CategoryResponseMapper categoryResponseMapper) {
        this.userActiveCategoriesExtractor = userActiveCategoriesExtractor;
        this.categoryResponseMapper = categoryResponseMapper;
    }

    @Override
    public List<CategoryResponse> execute(Long userId) {
        List<Category> categories = userActiveCategoriesExtractor.getUserActiveCategories(userId);

        return categories
                .stream()
                .map(categoryResponseMapper::toResponse)
                .toList();
    }
}
