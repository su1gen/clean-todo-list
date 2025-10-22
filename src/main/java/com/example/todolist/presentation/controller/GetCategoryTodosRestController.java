package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.GetCategoryTodosDto;
import com.example.todolist.application.inbound.category.GetCategoryTodos;
import com.example.todolist.domain.model.todo.Todo;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import com.example.todolist.presentation.webmodels.TodoListItemWebModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.CATEGORIES)
class GetCategoryTodosRestController {

    private final GetCategoryTodos getCategoryTodos;

    GetCategoryTodosRestController(GetCategoryTodos getCategoryTodos) {
        this.getCategoryTodos = getCategoryTodos;
    }

    @GetMapping("/todos/{categoryId}")
    public ResponseEntity<List<TodoListItemWebModel>> getCategoryTodos(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "") String status,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        GetCategoryTodosDto getCategoryTodosDto = new GetCategoryTodosDto(status, categoryId, userDetails.getUserId());

        List<Todo> todos = getCategoryTodos.execute(getCategoryTodosDto);

        List<TodoListItemWebModel> response = todos.stream()
                .map(TodoListItemWebModel::from)
                .toList();

        return ResponseEntity.ok(response);
    }
}
