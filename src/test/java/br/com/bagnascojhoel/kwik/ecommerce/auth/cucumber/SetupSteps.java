package br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber;

import io.cucumber.java.Before;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SetupSteps {

  @Inject private AuthContext authContext;

  @Before
  public void reset() {
    authContext.reset();
  }
}
