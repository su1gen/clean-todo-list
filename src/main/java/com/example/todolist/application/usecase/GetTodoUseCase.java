package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.application.dto.TodoStatusResponse;
import com.example.todolist.domain.exception.TodoAccessDeniedException;
import com.example.todolist.domain.exception.TodoNotFoundException;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.repository.TodoRepository;
import org.springframework.stereotype.Component;


@Component
class GetTodoUseCase implements GetTodo {

    private final TodoRepository todoRepository;

    GetTodoUseCase(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Получить задачу по ID с проверкой прав доступа
     */
    @Override
    public TodoResponse execute(Long todoId, Long userId) {
        // 1. Найти задачу (включая удалённые)
        Todo todo = todoRepository.findByIdAndNotDeleted(todoId)
                .orElseThrow(() -> new TodoNotFoundException(todoId));
        
        // 2. Проверить права доступа
        if (!todo.belongsToUser(userId)) {
            throw new TodoAccessDeniedException(todoId, userId);
        }

        // 3. Преобразовать в DTO
        return mapToResponse(todo);
    }

    private TodoResponse mapToResponse(Todo todo) {
        return new TodoResponse(
                todo.getId().getValue(),
                todo.getTitle().getValue(),
                todo.getDescription(),
                todo.getCategoryId().getValue(),
                new TodoStatusResponse(
                        todo.getStatus().getId(),
                        todo.getStatus().getTitle()
                ),
                todo.getCreatedAt(),
                todo.getPlannedAt()
        );
    }
}
