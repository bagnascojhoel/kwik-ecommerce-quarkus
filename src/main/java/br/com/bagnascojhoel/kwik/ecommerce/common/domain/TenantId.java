package br.com.bagnascojhoel.kwik.ecommerce.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class TenantId {

  @Column(name = "tenant_id")
  private final Long id;

  @Transient private final String businessId;

  private TenantId() {
    this.id = null;
    this.businessId = null;
  }

  public static TenantId of(final String businessId) {
    return new TenantId(null, businessId);
  }

  public static TenantId of(final Long id, final String businessId) {
    return new TenantId(id, businessId);
  }
}
