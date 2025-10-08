-- liquibase formatted sql

-- changeset su1gen:1
ALTER TABLE categories
    ADD COLUMN user_id BIGINT,
    ADD CONSTRAINT fk_categories_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE;