package br.com.bagnascojhoel.kwik.ecommerce.tenant.application;

import br.com.bagnascojhoel.kwik.ecommerce.tenant.domain.Tenant;
import br.com.bagnascojhoel.kwik.ecommerce.tenant.domain.TenantId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class TenantApplicationService {

  @Transactional
  public void create(final TenantId tenantId, final String name) {
    Tenant.create(tenantId, name).persist();
  }

  public Tenant get(final TenantId tenantId) {
    return Tenant.findById(tenantId);
  }

  public List<Tenant> getAll() {
    return Tenant.listAll();
  }
}
