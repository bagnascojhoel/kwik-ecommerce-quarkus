package br.com.bagnascojhoel.kwik.ecommerce.product.infra_driven;

import br.com.bagnascojhoel.kwik.ecommerce.common.domain.TenantId;
import br.com.bagnascojhoel.kwik.ecommerce.common.domain.TenantNotFoundException;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.TenantContextHolder;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class TenantContextHolderImpl implements TenantContextHolder {

  private TenantEntity tenant;

  @Override
  public void loadTenant(TenantId tenantId) {
    this.tenant = TenantEntity.findOptionalByBusinessId(tenantId.getBusinessId())
        .orElseThrow(() -> new TenantNotFoundException(tenantId));
  }

  @Override
  public TenantId currentTenantId() {
    assert tenant != null : "Tenant not loaded";
    return TenantId.of(tenant.getEntityId(), tenant.getBusinessId());
  }
}
