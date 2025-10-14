package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CreateTodoDto;
import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.application.dto.TodoStatusResponse;
import com.example.todolist.domain.exception.CategoryNotFoundException;
import com.example.todolist.domain.exception.UserNotFoundException;
import com.example.todolist.domain.model.*;
import com.example.todolist.domain.repository.CategoryRepository;
import com.example.todolist.domain.repository.TodoRepository;
import com.example.todolist.domain.repository.UserRepository;
import com.example.todolist.presentation.mapper.TodoResponseMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Use Case: Создание новой задачи.
 * <p>
 * Бизнес-правила:
 * 1. Пользователь должен существовать
 * 2. Если указана категория, она должна существовать и принадлежать пользователю
 * 3. Title обязателен
 */
@Component
class CreateTodoUseCase implements CreateTodo {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TodoResponseMapper todoResponseMapper;

    CreateTodoUseCase(
            TodoRepository todoRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository, TodoResponseMapper todoResponseMapper
    ) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.todoResponseMapper = todoResponseMapper;
    }

    @Override
    public TodoResponse execute(CreateTodoDto request, Long userId) {
        // 1. Проверить существование пользователя
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        // 2. Если указана категория, проверить её существование и принадлежность пользователю
        if (request.categoryId() != null) {
            categoryRepository.findByIdAndNotDeleted(request.categoryId())
                    .filter(category -> category.belongsToUser(userId))
                    .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));
        }

        // 3. Создать доменную модель (валидация внутри)
        Todo todo = new Todo(
                TodoId.of(null),
                Title.of(request.title()),
                request.description(),
                CategoryId.of(request.categoryId()),
                UserId.of(userId),
                TodoStatus.CREATED,
                LocalDateTime.now(),
                null,
                request.plannedAt()
        );

        // 4. Сохранить
        Todo savedTodo = todoRepository.save(todo);

        // 5. Вернуть ответ
        return todoResponseMapper.toResponse(savedTodo);
    }
}
