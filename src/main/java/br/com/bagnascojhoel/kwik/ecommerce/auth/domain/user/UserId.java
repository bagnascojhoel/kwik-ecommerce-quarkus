package br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class UserId {

  private Long value;
}
