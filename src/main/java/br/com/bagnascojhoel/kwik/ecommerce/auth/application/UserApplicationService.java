package br.com.bagnascojhoel.kwik.ecommerce.auth.application;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.KwikAuthConfig;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserAuthenticationService;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
@Setter
public class UserApplicationService {

  private static final String ADMIN_USERNAME = "kwik-admin";

  private final UserAuthenticationService userAuthenticationService;

  private final UserRepository userRepository;

  private final KwikAuthConfig kwikAuthConfig;

  @Transactional
  public void onboardAdmin(@Observes StartupEvent startupEvent) {
    log.atInfo().log("onboarding admin user");
    userRepository.getByUsername(ADMIN_USERNAME)
        .ifPresentOrElse(user -> {
              user.changePassword(userAuthenticationService, kwikAuthConfig.adminPassword());
              userRepository.persist(user);
            },
            () -> {
              throw new IllegalStateException("Admin user not found");
            });
  }

}