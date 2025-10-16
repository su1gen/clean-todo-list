package com.example.todolist.presentation.mapper;

import com.example.todolist.presentation.webmodels.AuthResponseWebModel;
import com.example.todolist.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class AuthResponseMapper {
    public AuthResponseWebModel toResponse(User user) {
        return new AuthResponseWebModel(
                user.getId().getValue(),
                user.getEmail().getValue()
        );
    }
}
