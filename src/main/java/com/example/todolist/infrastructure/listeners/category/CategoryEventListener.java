package com.example.todolist.infrastructure.listeners.category;

import com.example.todolist.domain.model.category.events.CategoryCreatedEvent;
import com.example.todolist.domain.model.category.events.CategoryTitleRenamedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class CategoryEventListener {
    @EventListener
    void handle(CategoryCreatedEvent event) {
        System.out.println(event);
    }

    @EventListener
    void handle(CategoryTitleRenamedEvent event) {
        System.out.println(event);
    }
}
