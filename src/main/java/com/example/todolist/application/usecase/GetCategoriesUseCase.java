package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.repository.CategoryRepository;
import com.example.todolist.presentation.mapper.CategoryResponseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class GetCategoriesUseCase implements GetCategories{
    private final CategoryRepository categoryRepository;
    private final CategoryResponseMapper categoryResponseMapper;

    public GetCategoriesUseCase(CategoryRepository categoryRepository, CategoryResponseMapper categoryResponseMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryResponseMapper = categoryResponseMapper;
    }

    @Override
    public List<CategoryResponse> execute(Long userId) {
        List<Category> categories = categoryRepository.findByUserIdAndNotDeletedWithDescOrder(userId);

        return categories
                .stream()
                .map(categoryResponseMapper::toResponse)
                .toList();
    }
}
