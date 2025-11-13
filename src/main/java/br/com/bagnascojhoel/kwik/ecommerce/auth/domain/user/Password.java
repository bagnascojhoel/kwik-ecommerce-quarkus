package br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user;

public record Password(
    byte[] encryptedPassword,
    byte[] salt
) {

}
