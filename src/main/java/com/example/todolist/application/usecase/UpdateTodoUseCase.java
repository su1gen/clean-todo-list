package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.application.dto.UpdateTodoDto;
import com.example.todolist.application.inbound.todo.UpdateTodo;
import com.example.todolist.application.outbound.category.ActiveCategoryExtractor;
import com.example.todolist.domain.exception.CategoryNotFoundException;
import com.example.todolist.domain.exception.TodoAccessDeniedException;
import com.example.todolist.domain.exception.TodoNotFoundException;
import com.example.todolist.domain.model.CategoryId;
import com.example.todolist.domain.model.Title;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.model.TodoStatus;
import com.example.todolist.application.outbound.TodoRepository;
import com.example.todolist.presentation.mapper.TodoResponseMapper;
import org.springframework.stereotype.Component;

@Component
class UpdateTodoUseCase implements UpdateTodo {

    private final TodoRepository todoRepository;
    private final ActiveCategoryExtractor activeCategoryExtractor;
    private final TodoResponseMapper todoResponseMapper;

    UpdateTodoUseCase(
            TodoRepository todoRepository,
            ActiveCategoryExtractor activeCategoryExtractor, TodoResponseMapper todoResponseMapper
    ) {
        this.todoRepository = todoRepository;
        this.activeCategoryExtractor = activeCategoryExtractor;
        this.todoResponseMapper = todoResponseMapper;
    }

    @Override
    public TodoResponse execute(Long todoId, UpdateTodoDto request, Long userId) {
        // 1. Найти Todo
        Todo todo = todoRepository.findByIdAndNotDeleted(todoId)
                .orElseThrow(() -> new TodoNotFoundException(todoId));

        // 2. Проверить права доступа
        if (!todo.belongsToUser(userId)) {
            throw new TodoAccessDeniedException(todoId, userId);
        }

        // 3. Если указана новая категория, проверить её
        if (request.categoryId()!=null) {
            activeCategoryExtractor.getActiveCategoryById(request.categoryId())
                    .filter(category -> category.belongsToUser(userId))
                    .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));
        }

        // 4. Обновить через бизнес-метод
        Todo updatedTodo = todo.update(
                Title.of(request.title()),
                request.description(),
                CategoryId.of(request.categoryId()),
                TodoStatus.fromId(request.statusId()),
                request.plannedAt()
        );

        // 5. Сохранить
        Todo savedTodo = todoRepository.save(updatedTodo);

        // 6. Вернуть ответ
        return todoResponseMapper.toResponse(savedTodo);
    }
}
