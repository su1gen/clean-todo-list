package com.example.todolist.infrastructure.listeners;

import com.example.todolist.domain.model.category.events.CategoryCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class CategoryEventListener {
    @EventListener
    void listentEvent(CategoryCreatedEvent event) {
        System.out.println(event);
    }
}
