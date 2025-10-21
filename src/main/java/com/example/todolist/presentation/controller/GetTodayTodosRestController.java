package com.example.todolist.presentation.controller;

import com.example.todolist.application.inbound.todo.GetTodayTodos;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import com.example.todolist.presentation.webmodels.TodoWithCategoryListItemWebModel;
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
    public ResponseEntity<List<TodoWithCategoryListItemWebModel>> getTodayTodos(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<Todo> todos = getTodayTodos.execute(userDetails.getUserId());

        List<TodoWithCategoryListItemWebModel> response = todos.stream()
                .map(TodoWithCategoryListItemWebModel::from)
                .toList();

        return ResponseEntity.ok(response);
    }
}
