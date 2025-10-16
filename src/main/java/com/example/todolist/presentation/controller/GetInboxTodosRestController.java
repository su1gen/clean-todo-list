package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.TodoWithCategoryDto;
import com.example.todolist.application.inbound.todo.GetInboxTodos;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import com.example.todolist.presentation.webmodels.TodoWithCategoryResponseWebModel;
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
    public ResponseEntity<List<TodoWithCategoryResponseWebModel>> getInboxTodos(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<TodoWithCategoryDto> todos = getInboxTodos.execute(userDetails.getUserId());

        List<TodoWithCategoryResponseWebModel> response = todos.stream()
                .map(TodoWithCategoryResponseWebModel::from)
                .toList();

        return ResponseEntity.ok(response);
    }
}
