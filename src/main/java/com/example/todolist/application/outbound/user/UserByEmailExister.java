package com.example.todolist.application.outbound.user;

public interface UserByEmailExister {
    /**
     * Проверить существование по email
     */
    boolean existsByEmail(String email);
}
