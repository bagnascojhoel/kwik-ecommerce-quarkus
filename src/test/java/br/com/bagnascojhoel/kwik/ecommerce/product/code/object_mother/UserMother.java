package br.com.bagnascojhoel.kwik.ecommerce.product.code.object_mother;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.Password;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.User;
import java.nio.charset.StandardCharsets;

public class UserMother {

  public static User kwikAdmin() {
    return User.builder()
        .username("kwik-admin")
        .email("kwikadmin@gmail.com")
        .securePassword(
            new Password("password".getBytes(StandardCharsets.UTF_8), "salt".getBytes()))
        .build();
  }
}
