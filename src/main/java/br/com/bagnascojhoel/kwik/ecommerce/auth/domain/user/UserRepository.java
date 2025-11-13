package br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user;

import java.util.Optional;

public interface UserRepository {

  void persist(User user);

  Optional<User> getByUsername(String username);

  Optional<User> getByEmail(String email);

}
