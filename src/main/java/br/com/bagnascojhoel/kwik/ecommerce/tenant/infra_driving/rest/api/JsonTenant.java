package br.com.bagnascojhoel.kwik.ecommerce.tenant.infra_driving.rest.api;

import br.com.bagnascojhoel.kwik.ecommerce.common.infra_driving.rest.api.JsonMetadata;
import br.com.bagnascojhoel.kwik.ecommerce.tenant.domain.Tenant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonTenant {

  @Schema(examples = "machados-barber")
  private String id;

  @Schema(examples = "Machado's Barber Shop")
  private String name;

  private JsonMetadata metadata;

  public static JsonTenant create(@NonNull Tenant tenant) {
    return new JsonTenant(
        tenant.getId().getValue(), tenant.getName(), JsonMetadataImpl.create(tenant));
  }
}
