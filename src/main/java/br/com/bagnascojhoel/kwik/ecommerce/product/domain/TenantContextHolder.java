package br.com.bagnascojhoel.kwik.ecommerce.product.domain;

import br.com.bagnascojhoel.kwik.ecommerce.common.domain.TenantId;

public interface TenantContextHolder {

  void loadTenant(TenantId tenantId);

  TenantId currentTenantId();
}
