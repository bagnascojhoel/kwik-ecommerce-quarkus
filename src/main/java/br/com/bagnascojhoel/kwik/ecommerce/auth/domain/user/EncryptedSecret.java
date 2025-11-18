package br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user;

public class EncryptedSecret extends Secret {

  private final String value;

  public EncryptedSecret(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }
}
