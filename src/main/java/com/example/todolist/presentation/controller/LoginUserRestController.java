package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.LoginRequest;
import com.example.todolist.application.usecase.LoginUser;
import com.example.todolist.infrastructure.security.jwt.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.AUTH)
public class LoginUserRestController {
    private final LoginUser loginUser;
    private final JwtService jwtService;

    public LoginUserRestController(LoginUser loginUser, JwtService jwtService) {
        this.loginUser = loginUser;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        var tokenData = loginUser.execute(loginRequest);

        ResponseCookie cookie = ResponseCookie.from(jwtService.getCookieName(), tokenData.token())
                .httpOnly(true)
//                .secure(true)
                .path("/")
                .maxAge((int) (tokenData.expiresAt() - System.currentTimeMillis()) / 1000)
                .sameSite("Lax")
//                .sameSite("None")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }
}
