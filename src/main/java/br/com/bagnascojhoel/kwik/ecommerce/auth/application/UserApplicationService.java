package br.com.bagnascojhoel.kwik.ecommerce.auth.application;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.KwikAuthConfig;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.PasswordEncryptionService;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserConstants;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
@Setter
public class UserApplicationService {

  private final PasswordEncryptionService passwordEncryptionService;

  private final UserRepository userRepository;

  private final KwikAuthConfig kwikAuthConfig;

  @Transactional
  public void onboardAdmin(@Observes StartupEvent startupEvent) {
    log.atInfo().log("onboarding admin user with password={}", kwikAuthConfig.adminPassword().getValue());
    userRepository
        .getByUsername(UserConstants.KWIK_ADMIN_USERNAME)
        .ifPresentOrElse(
            user -> {
              user.changePassword(passwordEncryptionService, kwikAuthConfig.adminPassword());
              userRepository.persist(user);
            },
            () -> {
              throw new IllegalStateException("Admin user not found");
            });
  }
}
