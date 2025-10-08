package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.application.dto.CreateCategoryDto;
import com.example.todolist.domain.exception.UserNotFoundException;
import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.repository.CategoryRepository;
import com.example.todolist.domain.repository.UserRepository;
import jakarta.inject.Named;

/**
 * Use Case: Создание новой категории.
 * <p>
 * Бизнес-правила:
 * 1. Пользователь должен существовать
 * 2. Title не может быть пустым
 * 3. Категория привязывается к пользователю
 */
@Named
class CreateCategoryUseCase implements  CreateCategory{

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    CreateCategoryUseCase(
            CategoryRepository categoryRepository,
            UserRepository userRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    /**
     * Выполнить создание категории
     *
     * @param request данные категории
     * @param userId  ID текущего пользователя (из JWT)
     */
    @Override
    public CategoryResponse execute(CreateCategoryDto request, Long userId) {
        // 1. Проверить существование пользователя
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        // 2. Создать доменную модель (валидация внутри конструктора)
        Category category = new Category(request.title(), userId);

        // 3. Сохранить
        Category savedCategory = categoryRepository.save(category);

        // 4. Преобразовать в DTO
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
