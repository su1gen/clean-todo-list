package com.example.todolist.application.outbound.user;

import com.example.todolist.domain.model.user.User;

import java.util.Optional;

public interface UserByIdExtractor {
    /**
     * Найти по ID
     */
    Optional<User> findById(Long id);
}
