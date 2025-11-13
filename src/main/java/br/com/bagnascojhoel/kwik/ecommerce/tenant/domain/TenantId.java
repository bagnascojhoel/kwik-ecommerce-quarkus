package br.com.bagnascojhoel.kwik.ecommerce.tenant.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TenantId implements Serializable {

  @Getter
  @Column(name = "business_id")
  private final String value;

  private TenantId() {
    this.value = null;
  }

  public static TenantId with(String id) {
    return new TenantId(id);
  }
}
