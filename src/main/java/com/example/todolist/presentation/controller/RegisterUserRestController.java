package com.example.todolist.presentation.controller;


import com.example.todolist.domain.model.User;
import com.example.todolist.presentation.webmodels.AuthResponseWebModel;
import com.example.todolist.presentation.webmodels.RegisterWebModel;
import com.example.todolist.application.inbound.user.RegisterUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.AUTH)
class RegisterUserRestController {
    private final RegisterUser registerUser;

    RegisterUserRestController(RegisterUser registerUser) {
        this.registerUser = registerUser;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseWebModel> register(@RequestBody RegisterWebModel registerWebModel) {
        User user = registerUser.execute(registerWebModel);

        AuthResponseWebModel response = AuthResponseWebModel.from(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
