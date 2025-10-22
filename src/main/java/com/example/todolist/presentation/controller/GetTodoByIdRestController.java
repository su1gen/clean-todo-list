package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.GetTodoDto;
import com.example.todolist.application.inbound.todo.GetTodo;
import com.example.todolist.domain.model.todo.Todo;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import com.example.todolist.presentation.webmodels.TodoResponseWebModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.TODOS)
class GetTodoByIdRestController {
    private final GetTodo getTodo;

    GetTodoByIdRestController(GetTodo getTodo) {
        this.getTodo = getTodo;
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponseWebModel> getTodo(
            @PathVariable Long todoId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        GetTodoDto getTodoDto = new GetTodoDto(todoId, userDetails.getUserId());

        Todo todo = getTodo.execute(getTodoDto);

        TodoResponseWebModel response = TodoResponseWebModel.from(todo);

        return ResponseEntity.ok(response);
    }
}
