package br.com.bagnascojhoel.kwik.ecommerce.common.domain.identity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Embeddable
@Builder(access = AccessLevel.PROTECTED, toBuilder = true)
@Getter(AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Author {

  @Column(name = "created_by")
  private final String createdBy;

  @Column(name = "created_at")
  private final LocalDateTime createdAt;

  @Column(name = "modified_by")
  private final String modifiedBy;

  @Column(name = "modified_at")
  private final LocalDateTime modifiedAt;

  private Author() {
    this.createdBy = null;
    this.createdAt = null;
    this.modifiedBy = null;
    this.modifiedAt = null;
  }

}
