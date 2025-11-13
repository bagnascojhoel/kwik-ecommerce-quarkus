package br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driving.rest;

import br.com.bagnascojhoel.kwik.ecommerce.auth.application.JwtFlowApplicationService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthFlowJwtRestAdapter implements AuthFlowJwtRestApi {

  private final JwtFlowApplicationService jwtFlowApplicationService;

  @Override
  public JsonJwt login(JsonJwtLogin jsonJwtLogin) {
    return JsonJwt.of(
        jwtFlowApplicationService.login(jsonJwtLogin.toGenerateJwtCommand()).getToken());
  }
}
