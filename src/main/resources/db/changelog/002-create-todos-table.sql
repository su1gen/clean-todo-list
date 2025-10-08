--liquibase formatted sql

--changeset su1gen:1
CREATE TABLE todos (
                      id BIGSERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      description TEXT,
                      category_id BIGINT,
                      created_at TIMESTAMP DEFAULT now(),
                      deleted_at TIMESTAMP,
                      CONSTRAINT fk_todos_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);
