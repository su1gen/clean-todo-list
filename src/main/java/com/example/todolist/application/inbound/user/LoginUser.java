package com.example.todolist.application.inbound.user;

import com.example.todolist.presentation.webmodels.LoginWebModel;
import com.example.todolist.application.dto.TokenDto;

public interface LoginUser {
    TokenDto execute(LoginWebModel loginWebModel);
}
