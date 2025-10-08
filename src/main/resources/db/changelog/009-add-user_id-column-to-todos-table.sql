-- liquibase formatted sql

-- changeset su1gen:1
ALTER TABLE todos
    ADD COLUMN user_id BIGINT,
    ADD CONSTRAINT fk_todos_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE;