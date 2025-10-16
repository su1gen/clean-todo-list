package com.example.todolist.application.inbound.user;

import com.example.todolist.domain.model.User;
import com.example.todolist.presentation.webmodels.RegisterWebModel;

public interface RegisterUser {
    User execute(RegisterWebModel registerWebModel);
}
