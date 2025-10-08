-- liquibase formatted sql

-- changeset su1gen:1
CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT now()
);