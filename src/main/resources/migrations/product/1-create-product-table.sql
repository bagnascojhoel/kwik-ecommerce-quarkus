--liquibase formatted sql

-- changeset bagnascojhoel:product-1-create-product-table
CREATE TABLE product.product (
    id SERIAL PRIMARY KEY,
    state VARCHAR(30) NOT NULL,
    name VARCHAR(300) NOT NULL,
    description VARCHAR(70),
    price_in_brl NUMERIC NOT NULL,
    tenant_id INTEGER NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_by VARCHAR(50),
    modified_at TIMESTAMP,
    FOREIGN KEY (tenant_id) REFERENCES tenant.tenant (id)
);