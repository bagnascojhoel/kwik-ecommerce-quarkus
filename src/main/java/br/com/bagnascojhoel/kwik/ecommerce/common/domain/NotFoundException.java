package br.com.bagnascojhoel.kwik.ecommerce.common.domain;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

  private String code;

  public NotFoundException(String code, String message) {
    super(message);
    this.code = code;
  }
}
