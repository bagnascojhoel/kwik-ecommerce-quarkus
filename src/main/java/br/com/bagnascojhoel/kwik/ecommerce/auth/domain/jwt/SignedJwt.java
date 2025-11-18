package br.com.bagnascojhoel.kwik.ecommerce.auth.domain.jwt;

public record SignedJwt(String token) {
  public static SignedJwt of(String token) {
    return new SignedJwt(token);
  }
}
