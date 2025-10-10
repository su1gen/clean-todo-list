package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.TodoWithCategoryResponse;
import com.example.todolist.application.usecase.GetInboxTodos;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.TODOS)
class GetInboxTodosRestController {
    private final GetInboxTodos getInboxTodos;

    GetInboxTodosRestController(GetInboxTodos getInboxTodos) {
        this.getInboxTodos = getInboxTodos;
    }

    @GetMapping("/inbox")
    public ResponseEntity<List<TodoWithCategoryResponse>> getInboxTodos(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        var todos = getInboxTodos.execute(userDetails.getUserId());

        return ResponseEntity.ok(todos);
    }
}
