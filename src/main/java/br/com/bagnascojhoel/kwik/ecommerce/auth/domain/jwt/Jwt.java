package br.com.bagnascojhoel.kwik.ecommerce.auth.domain.jwt;

import java.time.Duration;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Jwt {

  private final SignedJwt signedToken;
  private final String issuer;
  private final String audience;
  private final Instant issuedAt;
  private final Instant expiresAt;

  public static Jwt generate() {
    return new Jwt(new SignedJwt("token"), null, null, null, null);
  }

  public static Jwt of(SignedJwt signedJwt) {
    return new Jwt(signedJwt, null, null, null, null);
  }

  public static Jwt of(String aString) {
    return new Jwt(new SignedJwt(aString), null, null, null, null);
  }

  public Duration getDuration() {
    return Duration.between(issuedAt, expiresAt);
  }
}
