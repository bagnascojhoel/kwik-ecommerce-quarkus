package br.com.bagnascojhoel.kwik.ecommerce.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GenerateJwtCommand {

  private final String userId;

  private final String password;
}
