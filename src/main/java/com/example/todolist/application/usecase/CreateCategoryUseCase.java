package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CreateCategoryDto;
import com.example.todolist.application.inbound.category.CreateCategory;
import com.example.todolist.application.outbound.category.CategoryNextIdExtractor;
import com.example.todolist.application.outbound.category.CategoryPersister;
import com.example.todolist.application.outbound.user.UserByIdExtractor;
import com.example.todolist.domain.exception.UserNotFoundException;
import com.example.todolist.domain.model.Title;
import com.example.todolist.domain.model.UserId;
import com.example.todolist.domain.model.category.Category;
import com.example.todolist.domain.model.category.events.CategoryCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher applicationEventPublisher;


    CreateCategoryUseCase(
            CategoryPersister categoryPersister,
            CategoryNextIdExtractor categoryNextIdExtractor,
            UserByIdExtractor userByIdExtractor, ApplicationEventPublisher applicationEventPublisher
    ) {
        this.categoryPersister = categoryPersister;
        this.categoryNextIdExtractor = categoryNextIdExtractor;
        this.userByIdExtractor = userByIdExtractor;
        this.applicationEventPublisher = applicationEventPublisher;
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

        final var categoryId = categoryNextIdExtractor.getNextCategoryId();

        // 2. Создать доменную модель (валидация внутри конструктора)
        Category category = new Category(
                categoryId,
                Title.of(createCategoryDto.title()),
                UserId.of(createCategoryDto.userId()),
                LocalDateTime.now(),
                null
        );
        final var event = new CategoryCreatedEvent(categoryId.getValue());
        applicationEventPublisher.publishEvent(event);
        // 3. Сохранить
        return categoryPersister.persist(category);
    }
}
