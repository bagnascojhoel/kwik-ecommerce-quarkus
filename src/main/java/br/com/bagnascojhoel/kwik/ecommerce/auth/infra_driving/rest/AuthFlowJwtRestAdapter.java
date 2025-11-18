package br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driving.rest;

import br.com.bagnascojhoel.kwik.ecommerce.auth.application.JwtFlowApplicationService;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class AuthFlowJwtRestAdapter implements AuthFlowJwtRestApi {

  private final JwtFlowApplicationService jwtFlowApplicationService;

  @Override
  public Response login(JsonJwtLogin jsonJwtLogin) {
    Jwt jwt = jwtFlowApplicationService.login(jsonJwtLogin.toGenerateJwtCommand());
    JsonJwt jsonJwt = JsonJwt.of(jwt.getToken());
    NewCookie cookie =
        new NewCookie.Builder("jwt").value(jwt.getToken()).path("/").httpOnly(true).build();
    return Response.ok(jsonJwt).cookie(cookie).build();
  }

  @Override
  public Response refresh(@CookieParam("jwt") String token) {
    Jwt jwt = jwtFlowApplicationService.refresh(Jwt.of(token));
    JsonJwt jsonJwt = JsonJwt.of(jwt.getToken());
    NewCookie cookie =
        new NewCookie.Builder("jwt").value(jwt.getToken()).path("/").httpOnly(true).build();
    return Response.ok(jsonJwt).cookie(cookie).build();
  }

  @Override
  public Response checkState(@CookieParam("jwt") String token) {
    boolean valid = jwtFlowApplicationService.checkState(Jwt.of(token));
    JsonCheckState jsonCheckState = JsonCheckState.of(valid);
    return Response.ok(jsonCheckState).build();
  }
}
