package com.example.todolist.application.outbound.user;

public interface UserNextIdExtractor {
    /**
     * получить следующий ID
     */
    Long getNextUserId();
}
