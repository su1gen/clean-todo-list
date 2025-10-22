package com.example.todolist.application.outbound.user;

import com.example.todolist.domain.model.user.User;

public interface UserPersister {
    /**
     * создать пользователя
     */
    User persist(User user);
}
