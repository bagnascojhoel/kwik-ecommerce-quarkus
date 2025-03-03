--liquibase formatted sql

-- changeset bagnascojhoel:product-2-create-product-photo-table

CREATE TABLE product_photo (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    product_id INTEGER NOT NULL,
    url VARCHAR(300) NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL,
    modified_by VARCHAR(50),
    modified_at DATETIME,
    FOREIGN KEY(product_id) REFERENCES product(id)
)