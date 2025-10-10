package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.CategoryWithTodosResponse;
import com.example.todolist.application.usecase.GetCategoryTodos;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.CATEGORIES)
class GetCategoryTodosRestController {

    private final GetCategoryTodos getCategoryTodos;

    GetCategoryTodosRestController(GetCategoryTodos getCategoryTodos) {
        this.getCategoryTodos = getCategoryTodos;
    }

    @GetMapping("/todos/{categoryId}")
    public ResponseEntity<CategoryWithTodosResponse> getCategoryTodos(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "") String status,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        var todos = getCategoryTodos.execute(userDetails.getUserId(), categoryId, status);

        return ResponseEntity.ok(todos);
    }
}
