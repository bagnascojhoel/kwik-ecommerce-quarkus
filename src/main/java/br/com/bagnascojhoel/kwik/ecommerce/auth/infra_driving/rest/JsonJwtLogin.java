package br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driving.rest;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.GenerateJwtCommand;
import java.nio.charset.StandardCharsets;
import lombok.Data;

@Data
public class JsonJwtLogin {

  private String user;

  private String password;

  public GenerateJwtCommand toGenerateJwtCommand() {
    return new GenerateJwtCommand(user, password);
  }
}
