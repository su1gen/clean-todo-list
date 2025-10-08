--liquibase formatted sql

--changeset su1gen:1
CREATE TABLE categories (
                      id BIGSERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      created_at TIMESTAMP DEFAULT now(),
                      deleted_at TIMESTAMP
);