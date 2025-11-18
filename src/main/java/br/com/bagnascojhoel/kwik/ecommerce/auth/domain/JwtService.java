package br.com.bagnascojhoel.kwik.ecommerce.auth.domain;

public interface JwtService {

  boolean isValid(Jwt token);

  Jwt generate();
}
