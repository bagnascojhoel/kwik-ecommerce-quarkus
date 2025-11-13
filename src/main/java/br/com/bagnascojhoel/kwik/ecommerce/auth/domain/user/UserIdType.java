package br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user;

public enum UserIdType {
  USERNAME,
  EMAIL;

  public static UserIdType identifyType(String aString) {
    if (aString.contains("@")) {
      return EMAIL;
    }

    return USERNAME;
  }
}
