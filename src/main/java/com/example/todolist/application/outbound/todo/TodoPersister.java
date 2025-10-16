package com.example.todolist.application.outbound.todo;

import com.example.todolist.domain.model.Todo;

public interface TodoPersister {
    /**
     * создать Todo
     */
    Todo persist(Todo todo);
}
