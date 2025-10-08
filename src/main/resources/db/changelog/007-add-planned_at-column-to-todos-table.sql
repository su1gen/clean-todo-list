--liquibase formatted sql

--changeset su1gen:1
ALTER TABLE todos ADD COLUMN planned_at TIMESTAMP;
--rollback ALTER TABLE todos DROP COLUMN planned_at;