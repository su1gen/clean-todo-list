package com.example.todolist.infrastructure.listeners.category;

import com.example.todolist.domain.model.category.events.CategoryCreatedEvent;
import com.example.todolist.domain.model.category.events.CategoryTitleRenamedEvent;
import com.example.todolist.infrastructure.persistence.repository.JpaTodoRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class CategoryEventListener {
    private final JpaTodoRepository todoRepository;

    CategoryEventListener(JpaTodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @EventListener
    void handle(CategoryCreatedEvent event) {
        System.out.println(event);
    }

    @EventListener
    void handle(CategoryTitleRenamedEvent event) {
        todoRepository.updateCategoryTitle(event.categoryId(), event.newTitle());
        System.out.println(event);
    }
}
