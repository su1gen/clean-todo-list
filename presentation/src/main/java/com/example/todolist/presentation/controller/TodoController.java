package com.example.todolist.presentation.controller;

import com.example.todolist.application.usecase.CreateTodo;
import com.example.todolist.application.usecase.DeleteTodo;
import com.example.todolist.application.usecase.GetTodo;
import com.example.todolist.application.usecase.GetUserTodos;
import com.example.todolist.application.dto.CreateTodoDto;
import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.application.dto.UpdateTodoRequest;
import com.example.todolist.application.usecase.*;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST контроллер для работы с Todo.
 * Все эндпоинты требуют аутентификации.
 * <p>
 * Эндпоинты:
 * - POST   /api/todos                  - Создать Todo
 * - GET    /api/todos                  - Получить все Todo пользователя
 * - GET    /api/todos/{id}             - Получить Todo по ID
 * - GET    /api/todos/status/{status}  - Получить Todo по статусу
 * - GET    /api/todos/category/{categoryId} - Получить Todo по категории
 * - GET    /api/todos/overdue          - Получить просроченные Todo
 * - GET    /api/todos/today            - Получить запланированные на сегодня
 * - PUT    /api/todos/{id}             - Обновить Todo
 * - DELETE /api/todos/{id}             - Удалить Todo (soft delete)
 * - POST   /api/todos/{id}/restore     - Восстановить Todo
 */
@RestController
@RequestMapping(ApiPaths.TODOS)
public class TodoController {

    private final CreateTodo createTodoUseCase;
    private final GetUserTodos getUserTodosUseCase;
    private final GetTodo getTodoUseCase;
    private final UpdateTodo updateTodoUseCase;
    private final DeleteTodo deleteTodoUseCase;

    public TodoController(
            CreateTodo createTodo,
            GetUserTodos getUserTodos,
            GetTodo getTodo,
            UpdateTodo updateTodo,
            DeleteTodo deleteTodo
    ) {
        this.createTodoUseCase = createTodo;
        this.getUserTodosUseCase = getUserTodos;
        this.getTodoUseCase = getTodo;
        this.updateTodoUseCase = updateTodo;
        this.deleteTodoUseCase = deleteTodo;
    }

    /**
     * POST /api/todos
     * Создать новую Todo
     */
    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(
            @RequestBody CreateTodoDto request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        TodoResponse response = createTodoUseCase.execute(
                request,
                userDetails.getUserId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/todos
     * Получить все Todo текущего пользователя
     */
    @GetMapping
    public ResponseEntity<List<TodoResponse>> getUserTodos(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<TodoResponse> todos = getUserTodosUseCase.execute(
                userDetails.getUserId()
        );
        return ResponseEntity.ok(todos);
    }

    /**
     * GET /api/todos/{id}
     * Получить Todo по ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        TodoResponse response = getTodoUseCase.execute(
                id,
                userDetails.getUserId()
        );
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/todos/status/{status}
     * Получить Todo по статусу (например: /api/todos/status/in-process)
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TodoResponse>> getTodosByStatus(
            @PathVariable String status,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<TodoResponse> todos = getUserTodosUseCase.executeByStatus(
                userDetails.getUserId(),
                status
        );
        return ResponseEntity.ok(todos);
    }

    /**
     * GET /api/todos/category/{categoryId}
     * Получить Todo по категории
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<TodoResponse>> getTodosByCategory(
            @PathVariable Long categoryId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<TodoResponse> todos = getUserTodosUseCase.executeByCategory(
                userDetails.getUserId(),
                categoryId
        );
        return ResponseEntity.ok(todos);
    }

    /**
     * GET /api/todos/overdue
     * Получить просроченные Todo
     */
    @GetMapping("/overdue")
    public ResponseEntity<List<TodoResponse>> getOverdueTodos(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<TodoResponse> todos = getUserTodosUseCase.executeOverdue(
                userDetails.getUserId()
        );
        return ResponseEntity.ok(todos);
    }

    /**
     * GET /api/todos/today
     * Получить запланированные на сегодня Todo
     */
    @GetMapping("/today")
    public ResponseEntity<List<TodoResponse>> getTodayTodos(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<TodoResponse> todos = getUserTodosUseCase.executeToday(
                userDetails.getUserId()
        );
        return ResponseEntity.ok(todos);
    }

    /**
     * PUT /api/todos/{id}
     * Обновить Todo
     */
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @PathVariable Long id,
            @RequestBody UpdateTodoRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        TodoResponse response = updateTodoUseCase.execute(
                id,
                request,
                userDetails.getUserId()
        );
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/todos/{id}
     * Удалить Todo (soft delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        deleteTodoUseCase.execute(id, userDetails.getUserId());
        return ResponseEntity.noContent().build();
    }

    /**
     * POST /api/todos/{id}/restore
     * Восстановить удалённую Todo
     */
    @PostMapping("/{id}/restore")
    public ResponseEntity<Void> restoreTodo(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        deleteTodoUseCase.restore(id, userDetails.getUserId());
        return ResponseEntity.noContent().build();
    }
}
