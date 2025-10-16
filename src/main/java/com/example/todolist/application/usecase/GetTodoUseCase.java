package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.GetTodoDto;
import com.example.todolist.application.inbound.todo.GetTodo;
import com.example.todolist.application.outbound.todo.ActiveTodoExtractor;
import com.example.todolist.domain.exception.TodoAccessDeniedException;
import com.example.todolist.domain.exception.TodoNotFoundException;
import com.example.todolist.domain.model.Todo;
import org.springframework.stereotype.Component;

@Component
class GetTodoUseCase implements GetTodo {

    private final ActiveTodoExtractor activeTodoExtractor;

    GetTodoUseCase(ActiveTodoExtractor activeTodoExtractor) {
        this.activeTodoExtractor = activeTodoExtractor;
    }

    /**
     * Получить задачу по ID с проверкой прав доступа
     */
    @Override
    public Todo execute(GetTodoDto getTodoDto) {
        // 1. Найти задачу (включая удалённые)
        Todo todo = activeTodoExtractor.findById(getTodoDto.todoId())
                .orElseThrow(() -> new TodoNotFoundException(getTodoDto.todoId()));
        
        // 2. Проверить права доступа
        if (!todo.belongsToUser(getTodoDto.userId())) {
            throw new TodoAccessDeniedException(getTodoDto.todoId(), getTodoDto.userId());
        }

        return todo;
    }
}
