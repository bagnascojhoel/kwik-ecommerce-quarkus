package br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber;

import br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber.mocks.JwtServiceMock;
import br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber.mocks.UserRepositoryMock;
import io.quarkus.test.junit.QuarkusTestProfile;
import java.util.Map;
import java.util.Set;

public class CucumberTestProfile implements QuarkusTestProfile {

  /**
   * Returns additional config to be applied to the test. This will override any existing config
   * (including in application.properties), however existing config will be merged with this (i.e.
   * application.properties config will still take effect, unless a specific config key has been
   * overridden).
   */
  @Override
  public Map<String, String> getConfigOverrides() {
    return Map.of(
        "quarkus.liquibase.enabled", "false",
        "quarkus.arc.exclude-types",
            "br.com.bagnascojhoel.kwik.ecommerce.product.*,br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driven.*,br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driving.*,br.com.bagnascojhoel.kwik.ecommerce.infra_shared.*,br.com.bagnascojhoel.kwik.ecommerce.tenant.*",
        "quarkus.hibernate-orm.enabled", "false",
        "quarkus.hibernate-orm.active", "false");
  }

  @Override
  public Set<Class<?>> getEnabledAlternatives() {
    return Set.of(UserRepositoryMock.class, JwtServiceMock.class);
  }

  @Override
  public boolean disableApplicationLifecycleObservers() {
    return true;
  }
}
