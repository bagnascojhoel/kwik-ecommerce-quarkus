package br.com.bagnascojhoel.kwik.ecommerce.product.domain;


public class ProductNotFoundException extends AbstractResourceNotFoundException {
    public ProductNotFoundException() {
        super("The product was not found", "product-not-found");
    }
}
