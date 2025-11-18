--liquibase formatted sql

-- changeset bagnascojhoel:product-2-create-product-photo-table

CREATE TABLE product.product_photo (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL,
    url VARCHAR(300) NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_by VARCHAR(50),
    modified_at TIMESTAMP,
    FOREIGN KEY(product_id) REFERENCES product.product(id)
)