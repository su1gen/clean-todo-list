package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.application.usecase.GetNonCategoryTodos;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.TODOS)
class GetNonCategoryTodosRestController {
    private final GetNonCategoryTodos getNonCategoryTodos;

    GetNonCategoryTodosRestController(GetNonCategoryTodos getNonCategoryTodos) {
        this.getNonCategoryTodos = getNonCategoryTodos;
    }

    @GetMapping("/without-category")
    public ResponseEntity<List<TodoResponse>> getWithoutCategoryTodos(
            @RequestParam(defaultValue = "") String status,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        var todos = getNonCategoryTodos.execute(userDetails.getUserId(), status);

        return ResponseEntity.ok(todos);
    }
}
