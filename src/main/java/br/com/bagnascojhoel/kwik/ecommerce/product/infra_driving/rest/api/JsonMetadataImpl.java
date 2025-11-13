package br.com.bagnascojhoel.kwik.ecommerce.product.infra_driving.rest.api;

import br.com.bagnascojhoel.kwik.ecommerce.common.infra_driving.rest.api.JsonMetadata;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.Product;
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

  public static JsonMetadataImpl create(Product product) {
    assert product.getAuthor() != null;
    assert product.getAuthor().getCreatedAt() != null;
    assert product.getAuthor().getCreatedBy() != null;
    return JsonMetadataImpl.builder()
        .createdBy(product.getAuthor().getCreatedBy())
        .createdAt(product.getAuthor().getCreatedAt())
        .modifiedBy(product.getAuthor().getModifiedBy())
        .modifiedAt(product.getAuthor().getModifiedAt())
        .build();
  }
}
