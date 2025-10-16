package com.example.todolist.application.outbound.todo;

import com.example.todolist.domain.model.Todo;

public interface TodoUpdater {
    /**
     * обновить Todo
     */
    Todo update(Todo todo);
}
