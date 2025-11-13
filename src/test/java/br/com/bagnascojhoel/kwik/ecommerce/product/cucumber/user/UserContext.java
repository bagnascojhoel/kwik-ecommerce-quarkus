package br.com.bagnascojhoel.kwik.ecommerce.product.cucumber.user;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.User;
import br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driven.database.UserPanacheRepository;
import io.quarkus.test.InjectMock;
import org.mockito.Mockito;

public class UserContext {

  public User currentUser;

  @InjectMock
  private UserPanacheRepository userRepository;

  public void verifyNoRegister() {
    Mockito.verifyNoInteractions(this.userRepository);
  }

  public void doJwtLogin(User user) {
  }
}
