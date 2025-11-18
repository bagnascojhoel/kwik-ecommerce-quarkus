--liquibase formatted sql

-- changeset bagnascojhoel:auth-2-add-kwik-user context:auth
INSERT INTO auth."user" (username, email, password_hash, created_by, created_at)
VALUES ('kwik-admin', 'kwik@example.com', '<hash>', 'liquibase', CURRENT_TIMESTAMP);