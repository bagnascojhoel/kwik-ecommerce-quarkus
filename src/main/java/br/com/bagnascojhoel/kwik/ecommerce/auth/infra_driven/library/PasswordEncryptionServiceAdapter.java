package br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driven.library;

import static at.favre.lib.crypto.bcrypt.BCrypt.Version.VERSION_2A;

import java.security.SecureRandom;
import java.util.Arrays;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.EncryptedSecret;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.PasswordEncryptionService;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.RawSecret;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class PasswordEncryptionServiceAdapter implements PasswordEncryptionService {

  private static final SecureRandom SECURE_RANDOM = new SecureRandom();

  private static final BCrypt.Hasher HASHER = BCrypt.with(VERSION_2A);

  private static final int COST = 4;

  private static final int SALT_LENGTH = 16;

  @Override
  public boolean equals(@Nonnull EncryptedSecret encrypted, @Nonnull RawSecret raw) {
    BCrypt.Result result = BCrypt.verifyer()
      .verify(raw.value().getBytes(), encrypted.value().getBytes());
    return result.verified;
  }

  @Override
  public EncryptedSecret encryptPassword(@Nonnull RawSecret raw) {
    byte[] salt = new byte[SALT_LENGTH];
    SECURE_RANDOM.nextBytes(salt);
    byte[] encryptedPassword = HASHER.hash(COST, salt, raw.value().getBytes());
    String result = new String(encryptedPassword);
    Arrays.fill(salt, (byte) 0);
    Arrays.fill(encryptedPassword, (byte) 0);
    return new EncryptedSecret(result);
  }
}
