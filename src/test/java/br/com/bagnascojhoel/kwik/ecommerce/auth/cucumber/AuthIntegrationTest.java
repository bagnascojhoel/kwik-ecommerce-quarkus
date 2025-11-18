package br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserRepository;
import io.quarkiverse.cucumber.CucumberOptions;
import io.quarkiverse.cucumber.CucumberQuarkusTest;
import io.quarkus.test.InjectMock;

@CucumberOptions(
    features = "src/test/resources/features/auth/",
    glue = {
      "br.com.bagnascojhoel.kwik.ecommerce.common.cucumber",
      "br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber"
    })
public class AuthIntegrationTest extends CucumberQuarkusTest {

  @InjectMock private UserRepository userRepository;

  public static void main(String[] args) {
    runMain(AuthIntegrationTest.class, args);
  }
}
