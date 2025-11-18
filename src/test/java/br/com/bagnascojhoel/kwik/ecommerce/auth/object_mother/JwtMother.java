package br.com.bagnascojhoel.kwik.ecommerce.auth.object_mother;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.Jwt;

public class JwtMother {

  public static Jwt expiredJwt() {
    return Jwt.generate();
  }

  public static Jwt validJwt() {
    return Jwt.generate();
  }
}
