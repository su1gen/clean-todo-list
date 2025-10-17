-- liquibase formatted sql

-- changeset su1gen:1
ALTER TABLE todos
    ADD COLUMN category_title VARCHAR(255) NOT NULL DEFAULT '';
