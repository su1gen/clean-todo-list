package com.example.todolist.application.usecase.todo;

import com.example.todolist.application.dto.UpdateTodoDto;
import com.example.todolist.application.inbound.todo.UpdateTodo;
import com.example.todolist.application.outbound.category.ActiveCategoryExtractor;
import com.example.todolist.application.outbound.todo.ActiveTodoExtractor;
import com.example.todolist.application.outbound.todo.TodoUpdater;
import com.example.todolist.domain.exception.TodoAccessDeniedException;
import com.example.todolist.domain.exception.TodoNotFoundException;
import com.example.todolist.domain.model.*;
import com.example.todolist.domain.model.category.Category;
import com.example.todolist.domain.model.category.CategoryId;
import com.example.todolist.domain.model.todo.Todo;
import com.example.todolist.domain.model.todo.TodoCategoryTitle;
import com.example.todolist.domain.model.todo.TodoStatus;
import com.example.todolist.domain.model.todo.events.TodoCreatedEvent;
import com.example.todolist.domain.model.todo.events.TodoUpdatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class UpdateTodoUseCase implements UpdateTodo {

    private final ActiveTodoExtractor activeTodoExtractor;
    private final TodoUpdater todoUpdater;
    private final ActiveCategoryExtractor activeCategoryExtractor;
    private final ApplicationEventPublisher applicationEventPublisher;

    UpdateTodoUseCase(ActiveTodoExtractor activeTodoExtractor, TodoUpdater todoUpdater, ActiveCategoryExtractor activeCategoryExtractor, ApplicationEventPublisher applicationEventPublisher) {
        this.activeTodoExtractor = activeTodoExtractor;
        this.todoUpdater = todoUpdater;
        this.activeCategoryExtractor = activeCategoryExtractor;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public Todo execute(UpdateTodoDto updateTodoDto) {
        // 1. Найти Todo
        Todo todo = activeTodoExtractor.findById(updateTodoDto.todoId())
                .orElseThrow(() -> new TodoNotFoundException(updateTodoDto.todoId()));

        // 2. Проверить права доступа
        if (!todo.belongsToUser(updateTodoDto.userId())) {
            throw new TodoAccessDeniedException(updateTodoDto.todoId(), updateTodoDto.userId());
        }

        CategoryId categoryId = CategoryId.of(updateTodoDto.categoryId());
        Optional<Category> todoCategory = activeCategoryExtractor.getActiveCategoryById(categoryId);


        String categoryTitle = todoCategory
                .map(item -> item.getTitle().getValue())
                .orElse("");

        Todo updatedTodo = todo.update(
                Title.of(updateTodoDto.title()),
                updateTodoDto.description(),
                CategoryId.of(updateTodoDto.categoryId()),
                TodoCategoryTitle.of(categoryTitle),
                TodoStatus.fromId(updateTodoDto.statusId()),
                updateTodoDto.plannedAt()
        );

        Todo newTodoData = todoUpdater.update(updatedTodo);

        var event = new TodoUpdatedEvent(newTodoData.getId().getValue());
        applicationEventPublisher.publishEvent(event);

        // 5. Сохранить
        return newTodoData;
    }
}
