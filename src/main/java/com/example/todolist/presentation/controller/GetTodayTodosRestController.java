package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.TodoWithCategoryResponse;
import com.example.todolist.application.usecase.GetTodayTodos;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.TODOS)
class GetTodayTodosRestController {

    private final GetTodayTodos getTodayTodos;

    GetTodayTodosRestController(GetTodayTodos getTodayTodos) {
        this.getTodayTodos = getTodayTodos;
    }

    @GetMapping("/today")
    public ResponseEntity<List<TodoWithCategoryResponse>> getTodayTodos(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        var todos = getTodayTodos.execute(userDetails.getUserId());

        return ResponseEntity.ok(todos);
    }
}
