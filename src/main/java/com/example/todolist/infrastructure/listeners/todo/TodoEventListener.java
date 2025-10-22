package com.example.todolist.infrastructure.listeners.todo;

import com.example.todolist.domain.model.todo.events.TodoCreatedEvent;
import com.example.todolist.domain.model.todo.events.TodoUpdatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class TodoEventListener {
    @EventListener
    void handle(TodoCreatedEvent event) {
        System.out.println(event);
    }

    @EventListener
    void handle(TodoUpdatedEvent event) {
        System.out.println(event);
    }
}
