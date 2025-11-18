package br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.AuthenticationException;
import io.cucumber.java.en.Then;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ThenSteps {

  @Inject private AuthContext authContext;

  @Then("a JWT is issued")
  public void aJwtTokenIsIssued() {
    assertThat(authContext.getResult().getNewJwt()).isNotNull();
    assertThat(authContext.getResult().getException()).isNull();
  }

  @Then("no JWT is issued")
  public void noJwtTokenIsIssued() {
    assertThat(authContext.getResult().getNewJwt()).isNull();
    assertThat(authContext.getResult().getException()).isNull();
  }

  @Then("an authentication error is returned")
  public void anAuthenticationErrorIsReturned() {
    assertThat(authContext.getResult().getException())
        .isNotNull()
        .isInstanceOf(AuthenticationException.class);
  }

  @Then("the JWT is valid")
  public void theStateIsReportedAsValid() {
    assertThat(authContext.getResult().isValidJwt()).isTrue();
  }

  @Then("the JWT is not valid")
  public void theStateIsReportedAsInvalid() {
    assertThat(authContext.getResult().isValidJwt()).isFalse();
  }

  @Then("the JWT is valid for {int} minutes")
  public void theNewJWTTokenIsValidForMinutes(int minutes) {
    assertThat(authContext.getResult().getNewJwt().getDuration()).hasMinutes(minutes);
  }
}
