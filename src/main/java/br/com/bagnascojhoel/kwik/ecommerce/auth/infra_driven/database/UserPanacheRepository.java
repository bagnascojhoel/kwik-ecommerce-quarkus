package br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driven.database;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UserPanacheRepository implements PanacheRepository<UserEntity> {

  public Optional<UserEntity> getByUsername(String username) {
    return find("username", username).firstResultOptional();
  }

  public Optional<UserEntity> getByEmail(String email) {
    return find("email", email).firstResultOptional();
  }
}
