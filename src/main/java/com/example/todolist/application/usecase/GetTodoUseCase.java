package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.domain.exception.TodoAccessDeniedException;
import com.example.todolist.domain.exception.TodoNotFoundException;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class GetTodoUseCase {

    private final TodoRepository todoRepository;

    public GetTodoUseCase(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Получить задачу по ID с проверкой прав доступа
     */
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
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getCategoryId(),
                todo.getUserId(),
                todo.getStatus().getUrlParam(),
                todo.getStatus().getTitle(),
                todo.getCreatedAt(),
                todo.getPlannedAt(),
                todo.isDeleted(),
                todo.isOverdue()
        );
    }
}
