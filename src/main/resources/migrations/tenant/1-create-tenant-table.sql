--liquibase formatted sql

-- changeset bagnascojhoel:tenant-1-create-tenant-table
CREATE TABLE tenant (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    business_id VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(300) NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL,
    modified_by VARCHAR(50),
    modified_at DATETIME
);