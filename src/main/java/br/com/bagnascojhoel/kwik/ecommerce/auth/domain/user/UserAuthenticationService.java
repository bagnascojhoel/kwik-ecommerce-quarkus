package br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user;

public interface UserAuthenticationService {

  boolean hasCorrectPassword(User user, String rawPassword);

  Password generateSecurePassword(String rawPassword);
}
