package br.com.bagnascojhoel.kwik.ecommerce.auth.domain.jwt;

public interface JwtService {

  boolean isValid(Jwt token);

  Jwt generate();

  Jwt unwrap(SignedJwt signedToken);
}
