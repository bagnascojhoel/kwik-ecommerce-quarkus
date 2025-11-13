package br.com.bagnascojhoel.kwik.ecommerce.product.cucumber;

import br.com.bagnascojhoel.kwik.ecommerce.product.cucumber.user.UserContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import jakarta.inject.Inject;

public class Steps {

  @Inject private UserContext userContext;

  @Given("current user is the Kwik Admin")
  void givenCurrentUserIsTheKwikAdmin() {}

  @Given("no prior user register is made")
  void givenNoPriorUserRegisterIsMade() {
    this.userContext.verifyNoRegister();
  }

  @When("the admin user tries to login with default password")
  void whenTheAdminUserTriesToLoginWithDefaultPassword() {

    this.userContext.doJwtLogin(this.userContext.currentUser);
  }
}
