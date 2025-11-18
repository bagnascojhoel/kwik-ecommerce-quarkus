--liquibase formatted sql

-- changeset bagnascojhoel:auth-1-create-user-table context:auth
CREATE TABLE auth."user" (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(300) NOT NULL,
    password_hash VARCHAR(300) NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_by VARCHAR(50),
    modified_at TIMESTAMP
);