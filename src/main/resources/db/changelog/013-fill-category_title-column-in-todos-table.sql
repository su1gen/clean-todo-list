-- liquibase formatted sql

-- changeset su1gen:1
UPDATE todos
SET category_title = categories.title
    FROM categories
WHERE todos.category_id = categories.id;