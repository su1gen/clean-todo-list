package com.example.todolist.application.outbound.user;

import com.example.todolist.domain.model.User;

public interface UserPersister {
    /**
     * создать пользователя
     */
    User persist(User user);
}
