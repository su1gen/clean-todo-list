package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.UpdateTodoDto;
import com.example.todolist.application.inbound.todo.UpdateTodo;
import com.example.todolist.application.outbound.category.ActiveCategoryExtractor;
import com.example.todolist.application.outbound.todo.ActiveTodoExtractor;
import com.example.todolist.application.outbound.todo.TodoUpdater;
import com.example.todolist.domain.exception.CategoryNotFoundException;
import com.example.todolist.domain.exception.TodoAccessDeniedException;
import com.example.todolist.domain.exception.TodoNotFoundException;
import com.example.todolist.domain.model.CategoryId;
import com.example.todolist.domain.model.Title;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.model.TodoStatus;
import org.springframework.stereotype.Component;

@Component
class UpdateTodoUseCase implements UpdateTodo {

    private final ActiveTodoExtractor activeTodoExtractor;
    private final TodoUpdater todoUpdater;
    private final ActiveCategoryExtractor activeCategoryExtractor;

    UpdateTodoUseCase(ActiveTodoExtractor activeTodoExtractor, TodoUpdater todoUpdater, ActiveCategoryExtractor activeCategoryExtractor) {
        this.activeTodoExtractor = activeTodoExtractor;
        this.todoUpdater = todoUpdater;
        this.activeCategoryExtractor = activeCategoryExtractor;
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

        // 3. Если указана новая категория, проверить её
        if (updateTodoDto.categoryId() != null) {
            activeCategoryExtractor.getActiveCategoryById(updateTodoDto.categoryId())
                    .filter(category -> category.belongsToUser(updateTodoDto.userId()))
                    .orElseThrow(() -> new CategoryNotFoundException(updateTodoDto.categoryId()));
        }

        // 4. Обновить через бизнес-метод
        Todo updatedTodo = todo.update(
                Title.of(updateTodoDto.title()),
                updateTodoDto.description(),
                CategoryId.of(updateTodoDto.categoryId()),
                TodoStatus.fromId(updateTodoDto.statusId()),
                updateTodoDto.plannedAt()
        );

        // 5. Сохранить
        return todoUpdater.update(updatedTodo);
    }
}
