package br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driven;

import static at.favre.lib.crypto.bcrypt.BCrypt.Version.VERSION_2A;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.Password;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.User;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserAuthenticationService;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import java.security.SecureRandom;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

@Slf4j
@ApplicationScoped
public class UserAuthenticationServiceAdapter implements UserAuthenticationService {

  private static final SecureRandom SECURE_RANDOM = new SecureRandom();

  private static final BCrypt.Hasher HASHER = BCrypt.with(VERSION_2A);

  private static final int COST = 4;

  private static final int SALT_LENGTH = 16;

  @Override
  public boolean hasCorrectPassword(@Nonnull User user, @Nonnull String rawPassword) {
    Password actualPassword = user.getSecurePassword();
    byte[] saltedPassword = ArrayUtils.addAll(rawPassword.getBytes(), actualPassword.salt());
    byte[] encryptedPassword = HASHER.hash(COST, actualPassword.salt(), saltedPassword);
    boolean isCorrect = Arrays.equals(encryptedPassword, actualPassword.encryptedPassword());
    Arrays.fill(saltedPassword, (byte) 0);
    Arrays.fill(encryptedPassword, (byte) 0);
    return isCorrect;
  }

  @Override
  public Password generateSecurePassword(String rawPassword) {
    byte[] salt = new byte[SALT_LENGTH];
    SECURE_RANDOM.nextBytes(salt);
    byte[] saltedPassword = ArrayUtils.addAll(rawPassword.getBytes(), salt);
    byte[] encryptedPassword = HASHER.hash(COST, salt, saltedPassword);
    Password password = new Password(encryptedPassword, salt);
    Arrays.fill(saltedPassword, (byte) 0);
    return password;
  }

}
