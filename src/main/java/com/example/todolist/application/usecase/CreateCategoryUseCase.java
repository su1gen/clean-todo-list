package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CreateCategoryDto;
import com.example.todolist.application.inbound.category.CreateCategory;
import com.example.todolist.application.outbound.category.CategoryNextIdExtractor;
import com.example.todolist.application.outbound.category.CategoryPersister;
import com.example.todolist.application.outbound.user.UserByIdExtractor;
import com.example.todolist.domain.exception.UserNotFoundException;
import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.model.CategoryId;
import com.example.todolist.domain.model.Title;
import com.example.todolist.domain.model.UserId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Use Case: Создание новой категории.
 * <p>
 * Бизнес-правила:
 * 1. Пользователь должен существовать
 * 2. Title не может быть пустым
 * 3. Категория привязывается к пользователю
 */
@Component
class CreateCategoryUseCase implements CreateCategory {

    private final CategoryPersister categoryPersister;
    private final CategoryNextIdExtractor categoryNextIdExtractor;
    private final UserByIdExtractor userByIdExtractor;

    CreateCategoryUseCase(
            CategoryPersister categoryPersister,
            CategoryNextIdExtractor categoryNextIdExtractor,
            UserByIdExtractor userByIdExtractor
    ) {
        this.categoryPersister = categoryPersister;
        this.categoryNextIdExtractor = categoryNextIdExtractor;
        this.userByIdExtractor = userByIdExtractor;
    }

    /**
     * Выполнить создание категории
     */
    @Override
    public Category execute(CreateCategoryDto createCategoryDto) {
        // 1. Проверить существование пользователя
        if (userByIdExtractor.findById(createCategoryDto.userId()).isEmpty()) {
            throw new UserNotFoundException(createCategoryDto.userId());
        }

        Long categoryId = categoryNextIdExtractor.getNextCategoryId();

        // 2. Создать доменную модель (валидация внутри конструктора)
        Category category = new Category(
                CategoryId.of(categoryId),
                Title.of(createCategoryDto.title()),
                UserId.of(createCategoryDto.userId()),
                LocalDateTime.now(),
                null
        );

        // 3. Сохранить
        return categoryPersister.persist(category);
    }
}
