--liquibase formatted sql

-- changeset bagnascojhoel:tenant-1-create-tenant-table
CREATE TABLE tenant.tenant (
    id SERIAL PRIMARY KEY,
    business_id VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(300) NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_by VARCHAR(50),
    modified_at TIMESTAMP
);