package com.example.todolist.application.usecase.todo;

import com.example.todolist.application.dto.CreateTodoDto;
import com.example.todolist.application.inbound.todo.CreateTodo;
import com.example.todolist.application.outbound.category.ActiveCategoryExtractor;
import com.example.todolist.application.outbound.todo.TodoNextIdExtractor;
import com.example.todolist.application.outbound.todo.TodoPersister;
import com.example.todolist.application.outbound.user.UserByIdExtractor;
import com.example.todolist.domain.exception.UserNotFoundException;
import com.example.todolist.domain.model.category.Category;
import com.example.todolist.domain.model.category.CategoryId;
import com.example.todolist.domain.model.Title;
import com.example.todolist.domain.model.todo.Todo;
import com.example.todolist.domain.model.todo.TodoCategoryTitle;
import com.example.todolist.domain.model.todo.TodoId;
import com.example.todolist.domain.model.todo.TodoStatus;
import com.example.todolist.domain.model.todo.events.TodoCreatedEvent;
import com.example.todolist.domain.model.user.UserId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

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
    private final ApplicationEventPublisher applicationEventPublisher;

    CreateTodoUseCase(TodoNextIdExtractor todoNextIdExtractor,
                      UserByIdExtractor userByIdExtractor,
                      ActiveCategoryExtractor activeCategoryExtractor,
                      TodoPersister todoPersister,
                      ApplicationEventPublisher applicationEventPublisher) {
        this.todoNextIdExtractor = todoNextIdExtractor;
        this.userByIdExtractor = userByIdExtractor;
        this.activeCategoryExtractor = activeCategoryExtractor;
        this.todoPersister = todoPersister;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public Todo execute(CreateTodoDto createTodoDto) {
        // 1. Проверить существование пользователя
        if (userByIdExtractor.findById(createTodoDto.userId()).isEmpty()) {
            throw new UserNotFoundException(createTodoDto.userId());
        }

        CategoryId categoryId = CategoryId.of(createTodoDto.categoryId());
        Optional<Category> todoCategory = activeCategoryExtractor.getActiveCategoryById(categoryId);


        String categoryTitle = todoCategory
                .map(item -> item.getTitle().getValue())
                .orElse(TodoCategoryTitle.EMPTY_TITLE_VALUE);

        Long todoId = todoNextIdExtractor.getNextTodoId();

        // 3. Создать доменную модель (валидация внутри)
        Todo todo = new Todo(
                TodoId.of(todoId),
                Title.of(createTodoDto.title()),
                createTodoDto.description(),
                categoryId,
                TodoCategoryTitle.of(categoryTitle),
                UserId.of(createTodoDto.userId()),
                TodoStatus.CREATED,
                LocalDateTime.now(),
                null,
                createTodoDto.plannedAt());

        // 4. Сохранить
        Todo newTodo = todoPersister.persist(todo);

        var event = new TodoCreatedEvent(todoId);
        applicationEventPublisher.publishEvent(event);

        return newTodo;
    }
}
