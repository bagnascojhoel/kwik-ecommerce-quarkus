package br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value(staticConstructor = "of")
public class RawSecret extends Secret {

  private final String value;

  public RawSecret(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }
}
