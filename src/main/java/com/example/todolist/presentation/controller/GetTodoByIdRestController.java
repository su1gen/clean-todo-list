package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.application.inbound.todo.GetTodo;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.TODOS)
class GetTodoByIdRestController {
    private final GetTodo getTodo;

    GetTodoByIdRestController(GetTodo getTodo) {
        this.getTodo = getTodo;
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponse> getTodo(
            @PathVariable Long todoId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        final var todo = getTodo.execute(todoId, userDetails.getUserId());

        return ResponseEntity.ok(todo);
    }
}
