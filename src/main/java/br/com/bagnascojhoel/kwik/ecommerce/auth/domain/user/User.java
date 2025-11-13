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

  private Password securePassword;

  public void changePassword(
      UserAuthenticationService userAuthenticationService,
      String newPassword) {
    this.securePassword = userAuthenticationService.generateSecurePassword(newPassword);
  }
}
