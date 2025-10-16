package com.example.todolist.application.outbound.user;

import com.example.todolist.domain.model.User;

public interface UserUpdater {
    /**
     * Сохранить пользователя (создать или обновить)
     */
    User update(User user);
}
