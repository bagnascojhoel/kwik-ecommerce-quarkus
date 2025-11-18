package br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber.mocks;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.User;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import java.util.Optional;
import org.mockito.Mockito;

@ApplicationScoped
@Alternative
public class UserRepositoryMock extends Mocked<UserRepository> implements UserRepository {

  @Override
  public void persist(User user) {
    this.mockedInstance.persist(user);
  }

  @Override
  public Optional<User> getByUsername(String username) {
    return this.mockedInstance.getByUsername(username);
  }

  @Override
  public Optional<User> getByEmail(String email) {
    return this.mockedInstance.getByEmail(email);
  }

  @Override
  protected void initMock() {
    this.mockedInstance = Mockito.mock(UserRepository.class);
  }

  @Override
  public UserRepository mock() {
    return this.mockedInstance;
  }
}
