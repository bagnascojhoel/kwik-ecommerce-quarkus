package br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driven.database;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.User;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserId;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

  private final UserPanacheRepository userPanacheRepository;

  @Override
  public void persist(User user) {
    UserEntity userEntity =
        Optional.ofNullable(user.getUserId())
            .map(UserId::getValue)
            .map(userPanacheRepository::findByIdOptional)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(existingUser -> existingUser.updateWith(user))
            .orElseGet(() -> UserEntity.fromDomain(user));
    userPanacheRepository.persistAndFlush(userEntity);
  }

  @Override
  public Optional<User> getByUsername(String username) {
    return userPanacheRepository.getByUsername(username).map(UserEntity::toDomain);
  }

  @Override
  public Optional<User> getByEmail(String email) {
    return userPanacheRepository.getByEmail(email).map(UserEntity::toDomain);
  }
}
