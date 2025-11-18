package br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driven.database;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.EncryptedSecret;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.User;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "user")
@Table(schema = "auth")
public class UserEntity {

  @Id
  @Column(name = "id")
  private Long userId;

  private String username;

  private String email;

  @Column(name = "password_hash")
  private String encryptedPassword;

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
        .encryptedPassword(user.getSecurePassword().value())
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
        .encryptedPassword(user.getSecurePassword().value())
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
        .securePassword(new EncryptedSecret(encryptedPassword))
        .build();
  }

  public UserEntity updateWith(User user) {
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.encryptedPassword = user.getSecurePassword().value();
    this.modifiedBy = null;
    this.modifiedAt = Instant.now();
    return this;
  }
}
