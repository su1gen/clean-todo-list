package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.TodoStatusDto;
import com.example.todolist.presentation.webmodels.TodoStatusResponseWebModel;
import com.example.todolist.application.inbound.todo.GetTodoStatuses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.TODOS)
class GetTodoStatusesRestController {
    private final GetTodoStatuses getTodoStatuses;

    GetTodoStatusesRestController(GetTodoStatuses getTodoStatuses) {
        this.getTodoStatuses = getTodoStatuses;
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<TodoStatusResponseWebModel>> getStatuses() {
        List<TodoStatusDto> statuses = getTodoStatuses.execute();

        List<TodoStatusResponseWebModel> response = statuses.stream()
                .map(TodoStatusResponseWebModel::from)
                .toList();

        return ResponseEntity.ok(response);
    }
}
