package com.example.todolist.presentation.controller;

import com.example.todolist.domain.model.todo.Todo;
import com.example.todolist.presentation.webmodels.TodoResponseWebModel;
import com.example.todolist.application.dto.UpdateTodoDto;
import com.example.todolist.application.inbound.todo.UpdateTodo;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import com.example.todolist.presentation.webmodels.UpdateTodoWebModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.TODOS)
class UpdateTodoRestController {
    private final UpdateTodo updateTodo;

    UpdateTodoRestController(UpdateTodo updateTodo) {
        this.updateTodo = updateTodo;
    }


    @PutMapping("/{todoId}")
    public ResponseEntity<TodoResponseWebModel> editTodo(
            @PathVariable Long todoId,
            @RequestBody UpdateTodoWebModel updateTodoWebModel,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UpdateTodoDto updateTodoDto = updateTodoWebModel.toDto(todoId, userDetails.getUserId());

        Todo todo = updateTodo.execute(updateTodoDto);

        TodoResponseWebModel response = TodoResponseWebModel.from(todo);

        return ResponseEntity.ok(response);
    }
}
