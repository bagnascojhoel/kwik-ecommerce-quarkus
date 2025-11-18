package br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driven.library;

import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.Jwt;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.JwtService;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.KwikAuthConfig;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class JwtServiceAdapter implements JwtService {

  @Inject
  private KwikAuthConfig kwikAuthConfig;

  private JwtConsumer jwtConsumer;

  protected void initializeConsumer(@Observes StartupEvent startupEvent) {
    jwtConsumer = new JwtConsumerBuilder()
        .setVerificationKey(new HmacKey(kwikAuthConfig.jwtSecret().getValue().getBytes()))
        .setExpectedIssuer(kwikAuthConfig.jwtIssuer())
        .setExpectedAudience(kwikAuthConfig.jwtAudience())
        .setRequireExpirationTime()
        .build();
  }

  @Override
  public boolean isValid(Jwt token) {
    try {
      jwtConsumer.processToClaims(token.getToken());
      return true;
    } catch (Exception e) {
      log.atDebug().log("JWT validation failed: {}", e.getMessage());
      return false;
    }
  }

  @Override
  public Jwt generate() {
    JwtClaimsBuilder builder = io.smallrye.jwt.build.Jwt.claims();
    builder.issuer(kwikAuthConfig.jwtIssuer());
    builder.audience(kwikAuthConfig.jwtAudience());
    builder.expiresIn(kwikAuthConfig.jwtDurationMinutes());
    String token = builder.signWithSecret(kwikAuthConfig.jwtSecret().getValue());
    return Jwt.of(token);
  }
}
