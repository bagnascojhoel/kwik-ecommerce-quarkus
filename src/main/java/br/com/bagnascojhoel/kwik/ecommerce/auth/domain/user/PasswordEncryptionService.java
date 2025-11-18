package br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user;

public interface PasswordEncryptionService {

  boolean equals(EncryptedSecret encrypted, RawSecret raw);

  EncryptedSecret encryptPassword(RawSecret raw);
}
