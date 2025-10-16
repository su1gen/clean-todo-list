package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.GetNonCategoryTodosDto;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.presentation.webmodels.TodoResponseWebModel;
import com.example.todolist.application.inbound.todo.GetNonCategoryTodos;
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
    public ResponseEntity<List<TodoResponseWebModel>> getWithoutCategoryTodos(
            @RequestParam(defaultValue = "") String status,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        GetNonCategoryTodosDto getNonCategoryTodosDto = new GetNonCategoryTodosDto(status, userDetails.getUserId());

        List<Todo> todos = getNonCategoryTodos.execute(getNonCategoryTodosDto);

        List<TodoResponseWebModel> response = todos.stream()
                .map(TodoResponseWebModel::from)
                .toList();

        return ResponseEntity.ok(response);
    }
}
