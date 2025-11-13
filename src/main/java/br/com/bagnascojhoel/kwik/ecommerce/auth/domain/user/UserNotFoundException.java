package br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user;

import br.com.bagnascojhoel.kwik.ecommerce.common.domain.NotFoundException;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException() {
    super("user.not.found", "user.not.found");
  }
}
