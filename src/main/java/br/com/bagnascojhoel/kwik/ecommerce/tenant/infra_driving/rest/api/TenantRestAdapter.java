package br.com.bagnascojhoel.kwik.ecommerce.tenant.infra_driving.rest.api;

import br.com.bagnascojhoel.kwik.ecommerce.common.infra_driving.rest.api.JsonApiFeedback;
import br.com.bagnascojhoel.kwik.ecommerce.tenant.application.TenantApplicationService;
import br.com.bagnascojhoel.kwik.ecommerce.tenant.domain.Tenant;
import br.com.bagnascojhoel.kwik.ecommerce.tenant.domain.TenantId;
import java.util.List;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@AllArgsConstructor
public class TenantRestAdapter implements TenantRestApi {

  private final TenantApplicationService tenantApplicationService;

  @Override
  public JsonApiFeedback post(JsonTenant jsonTenant) {
    tenantApplicationService.create(TenantId.with(jsonTenant.getId()), jsonTenant.getName());
    return JsonApiFeedback.ofCreated();
  }

  @Override
  public List<JsonTenant> get() {
    return tenantApplicationService.getAll().stream().map(JsonTenant::create).toList();
  }

  @Override
  public RestResponse<JsonTenant> get(String aTenantId) {
    Tenant tenant = tenantApplicationService.get(TenantId.with(aTenantId));
    return RestResponse.ok(JsonTenant.create(tenant));
  }
}
