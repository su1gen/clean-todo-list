package com.example.todolist.application.usecase;

import com.example.todolist.application.inbound.category.GetCategories;
import com.example.todolist.application.outbound.category.UserActiveCategoriesExtractor;
import com.example.todolist.domain.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class GetCategoriesUseCase implements GetCategories {
    private final UserActiveCategoriesExtractor userActiveCategoriesExtractor;

    public GetCategoriesUseCase(UserActiveCategoriesExtractor userActiveCategoriesExtractor) {
        this.userActiveCategoriesExtractor = userActiveCategoriesExtractor;
    }

    @Override
    public List<Category> execute(Long userId) {
        return userActiveCategoriesExtractor.getUserActiveCategories(userId);
    }
}
