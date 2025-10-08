package com.example.todolist.application.usecase;

import com.example.todolist.domain.exception.TodoAccessDeniedException;
import com.example.todolist.domain.exception.TodoNotFoundException;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.repository.TodoRepository;
import jakarta.inject.Named;

/**
 * Use Case: Удаление Todo (soft delete).
 */
@Named
class DeleteTodoUseCase implements DeleteTodo {

    private final TodoRepository todoRepository;

    DeleteTodoUseCase(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Soft delete Todo
     */
    @Override
    public void execute(Long todoId, Long userId) {
        // 1. Найти Todo
        Todo todo = todoRepository.findByIdAndNotDeleted(todoId)
                .orElseThrow(() -> new TodoNotFoundException(todoId));

        // 2. Проверить права доступа
        if (!todo.belongsToUser(userId)) {
            throw new TodoAccessDeniedException(todoId, userId);
        }

        // 3. Пометить как удалённую
        Todo deletedTodo = todo.markAsDeleted();

        // 4. Сохранить
        todoRepository.save(deletedTodo);
    }

    /**
     * Восстановить удалённую Todo
     */
    @Override
    public void restore(Long todoId, Long userId) {
        // 1. Найти Todo (включая удалённые)
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException(todoId));

        // 2. Проверить права доступа
        if (!todo.belongsToUser(userId)) {
            throw new TodoAccessDeniedException(todoId, userId);
        }

        // 3. Восстановить
        Todo restoredTodo = todo.restore();

        // 4. Сохранить
        todoRepository.save(restoredTodo);
    }
}