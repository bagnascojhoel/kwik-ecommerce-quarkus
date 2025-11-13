package br.com.bagnascojhoel.kwik.ecommerce.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Jwt {

  private final String token;

  public static Jwt generate() {
    return new Jwt("token");
  }
}
