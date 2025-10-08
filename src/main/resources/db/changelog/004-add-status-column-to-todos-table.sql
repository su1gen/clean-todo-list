--liquibase formatted sql

--changeset su1gen:1
ALTER TABLE todos ADD COLUMN status INT NULL;
--rollback ALTER TABLE todos DROP COLUMN status;