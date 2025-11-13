package br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driving.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class JsonJwt {

  private String token;
}
