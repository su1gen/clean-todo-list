package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.application.dto.CreateCategoryDto;
import com.example.todolist.application.inbound.category.CreateCategory;
import com.example.todolist.application.outbound.category.CategoryNextIdExtractor;
import com.example.todolist.application.outbound.category.CategoryPersister;
import com.example.todolist.application.outbound.UserRepository;
import com.example.todolist.domain.exception.UserNotFoundException;
import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.model.CategoryId;
import com.example.todolist.domain.model.Title;
import com.example.todolist.domain.model.UserId;
import com.example.todolist.presentation.mapper.CategoryResponseMapper;
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
    private final UserRepository userRepository;
    private final CategoryResponseMapper categoryResponseMapper;

    CreateCategoryUseCase(
            CategoryPersister categoryPersister, CategoryNextIdExtractor categoryNextIdExtractor,
            UserRepository userRepository, CategoryResponseMapper categoryResponseMapper
    ) {
        this.categoryPersister = categoryPersister;
        this.categoryNextIdExtractor = categoryNextIdExtractor;
        this.userRepository = userRepository;
        this.categoryResponseMapper = categoryResponseMapper;
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

        Long categoryId = categoryNextIdExtractor.getNextCategoryId();

        // 2. Создать доменную модель (валидация внутри конструктора)
        Category category = new Category(
                CategoryId.of(categoryId),
                Title.of(request.title()),
                UserId.of(userId),
                LocalDateTime.now(),
                null
        );

        // 3. Сохранить
        Category savedCategory = categoryPersister.persist(category);

        // 4. Преобразовать в DTO
        return categoryResponseMapper.toResponse(savedCategory);
    }
}
