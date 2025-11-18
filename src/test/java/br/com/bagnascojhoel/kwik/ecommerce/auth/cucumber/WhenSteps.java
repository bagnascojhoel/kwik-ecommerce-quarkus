package br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber;

import io.cucumber.java.en.When;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WhenSteps {

  @Inject private AuthContext authContext;

  @When("the login is attempted with correct username and password")
  public void theLoginIsAttemptedWithCorrectUsernameAndPassword() {
    authContext.loginWithUsername();
  }

  @When("the login is attempted with correct email and password")
  public void theLoginIsAttemptedWithCorrectEmailAndPassword() {
    authContext.loginWithEmail();
  }

  @When("the login is attempted with incorrect password")
  public void theLoginIsAttemptedWithIncorrectPassword() {
    authContext.loginWrongPassword();
  }

  @When("the refresh is attempted with the valid token")
  @When("the refresh is attempted with the invalid token")
  public void theRefreshIsAttemptedWithTheValidToken() {
    authContext.refresh();
  }

  @When("the check-state is attempted with the valid token")
  @When("the check-state is attempted with the invalid token")
  public void theCheckStateIsAttemptedWithTheValidToken() {
    authContext.checkState();
  }
}
