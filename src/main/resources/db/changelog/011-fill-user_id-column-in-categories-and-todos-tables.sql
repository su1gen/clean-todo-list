-- liquibase formatted sql

-- changeset su1gen:1
INSERT INTO users (email, password, created_at)
VALUES ('test@test.com', '$2a$10$85m1BojdO4Ae8wZkT8q3eOX/E.FAHukR0cbo/9eQlLgHyPtOKZ0GW', NOW());

-- Обновление таблицы categories
UPDATE categories
SET user_id = (SELECT id FROM users WHERE email = 'test@test.com' LIMIT 1)
WHERE user_id IS NULL;

-- Обновление таблицы todos
UPDATE todos
SET user_id = (SELECT id FROM users WHERE email = 'test@test.com' LIMIT 1)
WHERE user_id IS NULL;