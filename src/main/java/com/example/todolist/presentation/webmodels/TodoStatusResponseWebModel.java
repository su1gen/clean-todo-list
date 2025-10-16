package com.example.todolist.presentation.webmodels;

import com.example.todolist.application.dto.TodoStatusDto;

public record TodoStatusResponseWebModel(
        Integer id,
        String title
) {
    public static TodoStatusResponseWebModel from(TodoStatusDto todoStatusDto) {
        return new TodoStatusResponseWebModel(
                todoStatusDto.id(),
                todoStatusDto.title()
        );
    }
}
