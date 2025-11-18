package br.com.bagnascojhoel.kwik.ecommerce.auth.domain;

import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Jwt {

  private final String token;

  public static Jwt generate() {
    return new Jwt("token");
  }

  public static Jwt of(String aString) {
    return new Jwt(aString);
  }

  public Duration getDuration() {
    return Duration.ZERO;
  }
}
