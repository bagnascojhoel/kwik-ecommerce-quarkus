package br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driven.library;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.KwikAuthConfig;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.jwt.Jwt;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.jwt.JwtService;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.jwt.SignedJwt;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.time.Clock;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;

@ApplicationScoped
@Slf4j
public class JwtServiceAdapter implements JwtService {

  @Inject private KwikAuthConfig kwikAuthConfig;

  @Inject private Clock clock;

  private JwtConsumer jwtConsumer;

  protected void initializeConsumer(@Observes StartupEvent startupEvent) {
    jwtConsumer =
        new JwtConsumerBuilder()
            .setVerificationKey(new HmacKey(kwikAuthConfig.jwtSecret().getValue().getBytes()))
            .setExpectedIssuer(kwikAuthConfig.jwtIssuer())
            .setExpectedAudience(kwikAuthConfig.jwtAudience())
            .setRequireExpirationTime()
            .build();
  }

  @Override
  public boolean isValid(Jwt token) {
    try {
      jwtConsumer.processToClaims(token.getSignedToken().token());
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
    Instant now = Instant.now(clock);
    builder.issuedAt(now);
    String token = builder.signWithSecret(kwikAuthConfig.jwtSecret().getValue());
    return Jwt.builder()
        .issuer(kwikAuthConfig.jwtIssuer())
        .audience(kwikAuthConfig.jwtAudience())
        .expiresAt(now.plus(kwikAuthConfig.jwtDurationMinutes()))
        .issuedAt(now)
        .signedToken(SignedJwt.of(token))
        .build();
  }

  @Override
  public Jwt unwrap(SignedJwt signedToken) {
    try {
      JwtClaims jwtClaims = JwtClaims.parse(signedToken.token());
      String issuer = jwtClaims.getIssuer();
      String audience = jwtClaims.getAudience().getFirst();
      Instant issuedAt = Instant.ofEpochSecond(jwtClaims.getIssuedAt().getValue());
      Instant expiration = Instant.ofEpochSecond(jwtClaims.getExpirationTime().getValue());
      return new Jwt(signedToken, issuer, audience, issuedAt, expiration);
    } catch (Exception exception) {
      log.atInfo().log("Failed to unwrap JWT: {}", exception.getMessage());
      return Jwt.of(signedToken);
    }
  }
}
