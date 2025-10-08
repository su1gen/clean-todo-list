package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.application.dto.UpdateTodoRequest;
import com.example.todolist.domain.exception.CategoryNotFoundException;
import com.example.todolist.domain.exception.TodoAccessDeniedException;
import com.example.todolist.domain.exception.TodoNotFoundException;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.repository.CategoryRepository;
import com.example.todolist.domain.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UpdateTodoUseCase  {
    private final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;

    public UpdateTodoUseCase(
            TodoRepository todoRepository,
            CategoryRepository categoryRepository
    ) {
        this.todoRepository = todoRepository;
        this.categoryRepository = categoryRepository;
    }

    public TodoResponse execute(Long todoId, UpdateTodoRequest request, Long userId) {
        // 1. Найти Todo
        Todo todo = todoRepository.findByIdAndNotDeleted(todoId)
                .orElseThrow(() -> new TodoNotFoundException(todoId));

        // 2. Проверить права доступа
        if (!todo.belongsToUser(userId)) {
            throw new TodoAccessDeniedException(todoId, userId);
        }

        // 3. Если указана новая категория, проверить её
        if (request.categoryId() != null) {
            categoryRepository.findByIdAndNotDeleted(request.categoryId())
                    .filter(category -> category.belongsToUser(userId))
                    .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));
        }

        // 4. Обновить через бизнес-метод
        Todo updatedTodo = todo.update(
                request.title(),
                request.description(),
                request.categoryId(),
                request.plannedAt()
        );

        // 5. Сохранить
        Todo savedTodo = todoRepository.save(updatedTodo);

        // 6. Вернуть ответ
        return mapToResponse(savedTodo);
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
