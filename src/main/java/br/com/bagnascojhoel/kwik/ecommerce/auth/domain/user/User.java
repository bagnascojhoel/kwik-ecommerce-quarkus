package br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class User {

  private final UserId userId;

  private final String username;

  private final String email;

  private EncryptedSecret securePassword;

  public void changePassword(
      PasswordEncryptionService passwordEncryptionService, RawSecret password) {
    this.securePassword = passwordEncryptionService.encryptPassword(password);
  }
}
