package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.CategoryWithTodosDto;
import com.example.todolist.presentation.webmodels.CategoryWithTodosResponseWebModel;
import com.example.todolist.application.dto.GetCategoryTodosDto;
import com.example.todolist.application.inbound.todo.GetCategoryTodos;
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
    public ResponseEntity<CategoryWithTodosResponseWebModel> getCategoryTodos(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "") String status,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        GetCategoryTodosDto getCategoryTodosDto = new GetCategoryTodosDto(status, categoryId, userDetails.getUserId());

        CategoryWithTodosDto categoryWithTodosDto = getCategoryTodos.execute(getCategoryTodosDto);

        return ResponseEntity.ok(CategoryWithTodosResponseWebModel.from(categoryWithTodosDto));
    }
}
