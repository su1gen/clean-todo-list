package com.example.todolist.domain.exception;

/**
 * Исключение для попытки доступа к чужой категории.
 */
public class CategoryAccessDeniedException extends RuntimeException {
    public CategoryAccessDeniedException(Long categoryId, Long userId) {
        super("User " + userId + " does not have access to category " + categoryId);
    }
}
