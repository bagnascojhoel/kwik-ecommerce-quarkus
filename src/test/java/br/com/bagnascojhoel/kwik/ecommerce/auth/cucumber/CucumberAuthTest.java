package br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber;

import io.quarkiverse.cucumber.CucumberOptions;
import io.quarkiverse.cucumber.CucumberQuarkusTest;
import io.quarkus.test.junit.TestProfile;

@CucumberOptions(
    features = "src/test/resources/features/auth/",
    glue = {
      "br.com.bagnascojhoel.kwik.ecommerce.common.cucumber",
      "br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber"
    })
@TestProfile(CucumberTestProfile.class)
public class CucumberAuthTest extends CucumberQuarkusTest {

  public static void main(String[] args) {
    runMain(CucumberAuthTest.class, args);
  }
}
