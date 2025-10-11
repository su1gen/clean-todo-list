package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.UserResponse;
import com.example.todolist.application.outport.UserRepository;
import com.example.todolist.domain.exception.UserNotFoundException;
import com.example.todolist.domain.model.User;
import jakarta.inject.Named;

@Named
class GetUserUseCase implements GetUser {

    private final UserRepository userRepository;

    GetUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse executeById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

    @Override
    public UserResponse executeByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }
}
