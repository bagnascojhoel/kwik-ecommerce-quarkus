package br.com.bagnascojhoel.kwik.ecommerce.product.domain;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public class KwikEcommerceException extends RuntimeException {

  @Nullable
  private final String errorCode;

  public KwikEcommerceException(@Nonnull String message, String errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public KwikEcommerceException(@Nonnull String message, String errorCode, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }
}
