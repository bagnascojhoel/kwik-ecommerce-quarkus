package br.com.bagnascojhoel.kwik.ecommerce.auth.object_mother;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.PasswordEncryptionService;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.RawSecret;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.User;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserConstants;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMother {

  public static User douglasFonseca(PasswordEncryptionService passwordEncryptionService) {
    return User.builder()
        .username("douglas-fonseca")
        .email("douglasfonseca@gmail.com")
        .securePassword(passwordEncryptionService.encryptPassword(douglasFonsecaRawPassword()))
        .build();
  }

  public static RawSecret douglasFonsecaRawPassword() {
    return RawSecret.of("password");
  }

  public static User kwikAdmin(PasswordEncryptionService passwordEncryptionService) {
    return User.builder()
        .userId(UserId.of(1L))
        .username(UserConstants.KWIK_ADMIN_USERNAME)
        .email("kwikadmin@gmail.com")
        .securePassword(passwordEncryptionService.encryptPassword(RawSecret.of("password")))
        .build();
  }

  public static RawSecret kwikAdminRawPassword() {
    return RawSecret.of("password");
  }
}
