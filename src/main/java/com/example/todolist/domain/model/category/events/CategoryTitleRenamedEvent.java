package com.example.todolist.domain.model.category.events;

public record CategoryTitleRenamedEvent(Long categoryId, String newTitle) {
}
