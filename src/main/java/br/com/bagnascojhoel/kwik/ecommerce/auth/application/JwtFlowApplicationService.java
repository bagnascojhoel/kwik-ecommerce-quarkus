package br.com.bagnascojhoel.kwik.ecommerce.auth.application;

import java.util.Optional;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.AuthenticationException;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.GenerateJwtCommand;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.Jwt;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.JwtService;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.PasswordEncryptionService;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.RawSecret;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.User;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserIdType;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserNotFoundException;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserRepository;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class JwtFlowApplicationService {

  private final UserRepository userRepository;

  private final PasswordEncryptionService passwordEncryptionService;

  private final JwtService jwtService;

  public Jwt login(@Nonnull GenerateJwtCommand generateJwtCommand) {
    UserIdType userIdType = UserIdType.identifyType(generateJwtCommand.getUserId());
    Optional<User> user =
        switch (userIdType) {
          case EMAIL -> userRepository.getByEmail(generateJwtCommand.getUserId());
          case USERNAME -> userRepository.getByUsername(generateJwtCommand.getUserId());
        };

    if (user.isEmpty()) {
      throw new UserNotFoundException();
    }

    boolean hasCorrectPassword = passwordEncryptionService.equals(user.get().getSecurePassword(),
     new RawSecret(generateJwtCommand.getPassword()));
    if (!hasCorrectPassword) {
      log.atDebug().log(
          "password did not match, userId={}, password={}",
          user.get().getUserId(),
          generateJwtCommand.getPassword());
      throw new UserNotFoundException();
    }

    return jwtService.generate();
  }

  public Jwt refresh(@Nonnull Jwt token) {
    if (!jwtService.isValid(token)) {
      throw new AuthenticationException();
    }
    return jwtService.generate();
  }

  public boolean checkState(@Nonnull Jwt token) {
    return jwtService.isValid(token);
  }
}
