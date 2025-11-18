package br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber;

import io.cucumber.java.en.Given;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GivenSteps {

  @Inject private AuthContext authContext;

  @Given("the user exists")
  public void theUserExists() {
    authContext.setupUser();
  }

  @Given("the user has a valid JWT")
  public void theUserHasAValidJwt() {
    authContext.setupValidJwt();
  }

  @Given("the user has an invalid JWT")
  public void theUserHasAnInvalidJWT() {
    authContext.setupExpiredJwt();
  }
}
