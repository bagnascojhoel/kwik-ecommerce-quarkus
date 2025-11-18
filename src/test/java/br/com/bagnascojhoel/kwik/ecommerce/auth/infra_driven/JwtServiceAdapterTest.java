package br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driven;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.Jwt;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.KwikAuthConfig;
import br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driven.library.JwtServiceAdapter;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
class JwtServiceAdapterTest {

  @Inject
  JwtServiceAdapter jwtServiceAdapter;

  @Inject
  KwikAuthConfig kwikAuthConfig;

  @Test
  void generate_shouldReturnValidJwt() {
    Jwt jwt = jwtServiceAdapter.generate();

    assertThat(jwt).isNotNull();
    assertThat(jwt.getToken()).isNotNull().isNotEmpty();
    assertThat(jwtServiceAdapter.isValid(jwt)).isTrue();
  }

  @Test
  void isValid_shouldReturnFalseForInvalidToken() {
    Jwt invalidJwt = Jwt.of("invalid.token.here");

    assertThat(jwtServiceAdapter.isValid(invalidJwt)).isFalse();
  }

  @Test
  void isValid_shouldReturnFalseForExpiredToken() {
    JwtClaimsBuilder builder = io.smallrye.jwt.build.Jwt.claims();
    builder.issuer(kwikAuthConfig.jwtIssuer());
    builder.audience(kwikAuthConfig.jwtAudience());
    builder.expiresAt(System.currentTimeMillis() / 1000 - 60); // expired 1 minute ago
    String token = builder.signWithSecret(kwikAuthConfig.jwtSecret().getValue());
    Jwt expiredJwt = Jwt.of(token);

    assertThat(jwtServiceAdapter.isValid(expiredJwt)).isFalse();
  }
}
