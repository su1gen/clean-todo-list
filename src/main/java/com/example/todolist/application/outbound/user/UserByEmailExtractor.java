package com.example.todolist.application.outbound.user;

import com.example.todolist.domain.model.user.User;

import java.util.Optional;

public interface UserByEmailExtractor {
    /**
     * Найти по email
     */
    Optional<User> findByEmail(String email);
}
