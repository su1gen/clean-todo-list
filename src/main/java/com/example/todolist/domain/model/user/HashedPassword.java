package com.example.todolist.domain.model.user;

public record HashedPassword(String hash) {

    public static HashedPassword of(String hash) {
        if (hash.isBlank()) {
            throw new IllegalArgumentException("Password hash cannot be empty");
        }
        return new HashedPassword(hash);
    }

}
