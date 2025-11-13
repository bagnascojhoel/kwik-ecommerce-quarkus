package br.com.bagnascojhoel.kwik.ecommerce.common.infra_driven;

import br.com.bagnascojhoel.kwik.ecommerce.common.domain.TenantId;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.impl.GenerateBridge;
import java.util.Optional;

public interface PanacheTenantRepositoryBase<Entity, Id> extends PanacheRepositoryBase<Entity, Id> {

  @GenerateBridge
  default Optional<Entity> findByIdOptional(TenantId tenantId, Id id) {
    return this.find("tenantId = ?1 and id = ?2", tenantId, id).firstResultOptional();
  }

  @GenerateBridge
  default PanacheQuery<Entity> findAll(TenantId tenantId) {
    return this.find("tenantId", tenantId);
  }
}
