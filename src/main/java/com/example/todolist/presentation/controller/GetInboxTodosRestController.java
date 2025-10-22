package com.example.todolist.presentation.controller;

import com.example.todolist.application.inbound.todo.GetInboxTodos;
import com.example.todolist.domain.model.todo.Todo;
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
class GetInboxTodosRestController {
    private final GetInboxTodos getInboxTodos;

    GetInboxTodosRestController(GetInboxTodos getInboxTodos) {
        this.getInboxTodos = getInboxTodos;
    }

    @GetMapping("/inbox")
    public ResponseEntity<List<TodoWithCategoryListItemWebModel>> getInboxTodos(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<Todo> todos = getInboxTodos.execute(userDetails.getUserId());

        List<TodoWithCategoryListItemWebModel> response = todos.stream()
                .map(TodoWithCategoryListItemWebModel::from)
                .toList();

        return ResponseEntity.ok(response);
    }
}
