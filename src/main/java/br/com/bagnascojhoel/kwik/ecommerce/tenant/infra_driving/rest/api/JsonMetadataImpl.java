package br.com.bagnascojhoel.kwik.ecommerce.tenant.infra_driving.rest.api;

import br.com.bagnascojhoel.kwik.ecommerce.common.infra_driving.rest.api.JsonMetadata;
import br.com.bagnascojhoel.kwik.ecommerce.tenant.domain.Tenant;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class JsonMetadataImpl implements JsonMetadata {

  private String createdBy;

  private LocalDateTime createdAt;

  private String modifiedBy;

  private LocalDateTime modifiedAt;

  public static JsonMetadataImpl create(Tenant tenant) {
    assert tenant.getAuthor() != null;
    assert tenant.getAuthor().getCreatedAt() != null;
    assert tenant.getAuthor().getCreatedBy() != null;
    return JsonMetadataImpl.builder()
        .createdBy(tenant.getAuthor().getCreatedBy())
        .createdAt(tenant.getAuthor().getCreatedAt())
        .modifiedBy(tenant.getAuthor().getModifiedBy())
        .modifiedAt(tenant.getAuthor().getModifiedAt())
        .build();
  }
}
