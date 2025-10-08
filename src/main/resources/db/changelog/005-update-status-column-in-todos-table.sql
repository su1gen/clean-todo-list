--liquibase formatted sql

--changeset su1gen:2
UPDATE todos SET status = 3 WHERE status IS NULL;

ALTER TABLE todos ALTER COLUMN status SET DEFAULT 3;
ALTER TABLE todos ALTER COLUMN status SET NOT NULL;

--rollback ALTER TABLE todos ALTER COLUMN status DROP NOT NULL;
--rollback ALTER TABLE todos ALTER COLUMN status DROP DEFAULT;
--rollback UPDATE todos SET status = NULL WHERE status = 0;