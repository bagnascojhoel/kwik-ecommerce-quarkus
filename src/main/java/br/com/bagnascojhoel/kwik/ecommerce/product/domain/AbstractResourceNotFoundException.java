package br.com.bagnascojhoel.kwik.ecommerce.product.domain;

import jakarta.annotation.Nonnull;

public abstract class AbstractResourceNotFoundException extends KwikEcommerceException {
  public AbstractResourceNotFoundException(@Nonnull String message, String errorCode) {
    super(message, errorCode);
  }
}
