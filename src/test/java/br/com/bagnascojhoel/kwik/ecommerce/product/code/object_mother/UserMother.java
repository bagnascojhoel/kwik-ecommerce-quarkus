package br.com.bagnascojhoel.kwik.ecommerce.product.code.object_mother;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.EncryptedSecret;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.User;

public class UserMother {

  public static User kwikAdmin() {
    return User.builder()
        .username("kwik-admin")
        .email("kwikadmin@gmail.com")
        .securePassword(new EncryptedSecret("password"))
        .build();
  }
}
