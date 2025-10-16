package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CreateTodoDto;
import com.example.todolist.application.inbound.todo.CreateTodo;
import com.example.todolist.application.outbound.category.ActiveCategoryExtractor;
import com.example.todolist.application.outbound.todo.TodoNextIdExtractor;
import com.example.todolist.application.outbound.todo.TodoPersister;
import com.example.todolist.application.outbound.user.UserByIdExtractor;
import com.example.todolist.domain.exception.CategoryNotFoundException;
import com.example.todolist.domain.exception.UserNotFoundException;
import com.example.todolist.domain.model.*;
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

    private final TodoNextIdExtractor todoNextIdExtractor;
    private final UserByIdExtractor userByIdExtractor;
    private final ActiveCategoryExtractor activeCategoryExtractor;
    private final TodoPersister todoPersister;

    CreateTodoUseCase(TodoNextIdExtractor todoNextIdExtractor,
                      UserByIdExtractor userByIdExtractor,
                      ActiveCategoryExtractor activeCategoryExtractor,
                      TodoPersister todoPersister) {
        this.todoNextIdExtractor = todoNextIdExtractor;
        this.userByIdExtractor = userByIdExtractor;
        this.activeCategoryExtractor = activeCategoryExtractor;
        this.todoPersister = todoPersister;
    }


    @Override
    public Todo execute(CreateTodoDto createTodoDto) {
        // 1. Проверить существование пользователя
        if (userByIdExtractor.findById(createTodoDto.userId()).isEmpty()) {
            throw new UserNotFoundException(createTodoDto.userId());
        }

        // 2. Если указана категория, проверить её существование и принадлежность пользователю
        if (createTodoDto.categoryId() != null) {
            activeCategoryExtractor.getActiveCategoryById(createTodoDto.categoryId())
                    .filter(category -> category.belongsToUser(createTodoDto.userId()))
                    .orElseThrow(() -> new CategoryNotFoundException(createTodoDto.categoryId()));
        }

        Long todoId = todoNextIdExtractor.getNextTodoId();

        // 3. Создать доменную модель (валидация внутри)
        Todo todo = new Todo(
                TodoId.of(todoId),
                Title.of(createTodoDto.title()),
                createTodoDto.description(),
                CategoryId.of(createTodoDto.categoryId()),
                UserId.of(createTodoDto.userId()),
                TodoStatus.CREATED,
                LocalDateTime.now(),
                null,
                createTodoDto.plannedAt()
        );

        // 4. Сохранить
        return todoPersister.persist(todo);
    }
}
