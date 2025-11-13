package br.com.bagnascojhoel.kwik.ecommerce.common.domain;

import br.com.bagnascojhoel.kwik.ecommerce.product.domain.KwikEcommerceException;
import lombok.Getter;

@Getter
public class TenantNotFoundException extends KwikEcommerceException {

  private final TenantId tenantId;

  public TenantNotFoundException(TenantId tenantId) {
    super("Tenant not found", "tenant-not-found");
    this.tenantId = tenantId;
  }
}
