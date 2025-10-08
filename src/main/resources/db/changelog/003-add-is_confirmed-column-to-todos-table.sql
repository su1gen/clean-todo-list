--liquibase formatted sql

--changeset su1gen:1
ALTER TABLE todos ADD COLUMN is_confirmed BOOLEAN DEFAULT FALSE
--rollback ALTER TABLE todos DROP COLUMN is_confirmed;