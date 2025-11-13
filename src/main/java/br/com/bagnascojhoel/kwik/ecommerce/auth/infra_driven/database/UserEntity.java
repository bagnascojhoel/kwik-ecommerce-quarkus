package br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driven.database;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.Password;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.User;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "user")
public class UserEntity {

  @Id
  @Column(name = "id")
  private Long userId;

  private String username;

  private String email;

  @Column(name = "password_hash")
  private byte[] encryptedPassword;

  @Column(name = "password_salt")
  private byte[] salt;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "modified_by")
  private String modifiedBy;

  @Column(name = "modified_at")
  private Instant modifiedAt;

  public static UserEntity fromDomain(User user) {
    return UserEntity.builder()
        .userId(user.getUserId().getValue())
        .username(user.getUsername())
        .email(user.getEmail())
        .encryptedPassword(user.getSecurePassword().encryptedPassword())
        .salt(user.getSecurePassword().salt())
        .createdBy(null)
        .createdAt(Instant.now())
        .modifiedBy(null)
        .modifiedAt(Instant.now())
        .build();
  }

  public static UserEntity fromDomain(User user, UserEntity userEntity) {
    return UserEntity.builder()
        .userId(user.getUserId().getValue())
        .username(user.getUsername())
        .email(user.getEmail())
        .encryptedPassword(user.getSecurePassword().encryptedPassword())
        .salt(user.getSecurePassword().salt())
        .createdBy(userEntity.createdBy)
        .createdAt(userEntity.createdAt)
        .modifiedBy(null)
        .modifiedAt(Instant.now())
        .build();
  }

  public User toDomain() {
    return User.builder()
        .userId(UserId.of(userId))
        .email(email)
        .username(username)
        .securePassword(new Password(encryptedPassword, salt))
        .build();
  }

  public UserEntity updateWith(User user) {
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.encryptedPassword = user.getSecurePassword().encryptedPassword();
    this.salt = user.getSecurePassword().salt();
    this.modifiedBy = null;
    this.modifiedAt = Instant.now();
    return this;  
  }
}
