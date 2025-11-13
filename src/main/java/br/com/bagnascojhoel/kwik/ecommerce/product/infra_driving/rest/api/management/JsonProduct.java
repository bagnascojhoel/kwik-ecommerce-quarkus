package br.com.bagnascojhoel.kwik.ecommerce.product.infra_driving.rest.api.management;

import br.com.bagnascojhoel.kwik.ecommerce.common.infra_driving.rest.api.JsonMetadata;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.Photo;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.Product;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.ProductState;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.SaveProductCommand;
import br.com.bagnascojhoel.kwik.ecommerce.product.infra_driving.rest.api.JsonAmount;
import br.com.bagnascojhoel.kwik.ecommerce.product.infra_driving.rest.api.JsonMetadataImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

@Builder(access = AccessLevel.PRIVATE)
@Data
public class JsonProduct {

  private Long id;

  private String name;

  private String description;

  private ProductState state;

  private List<String> photoUrls;

  private JsonAmount price;

  private JsonMetadata metadata;

  public static JsonProduct create(@Nonnull Product product) {
    return JsonProduct.builder()
        .id(product.getId().value())
        .name(product.getName())
        .description(product.getDescription())
        .photoUrls(product.getPhotos().stream().map(Photo::getUrl).toList())
        .state(product.getState())
        .price(JsonAmount.ofBrl(product.getPriceInBrl()))
        .metadata(JsonMetadataImpl.create(product))
        .build();
  }

  public SaveProductCommand toSaveCommand() {
    return SaveProductCommand.builder()
        .name(name)
        .description(description)
        .photosUrl(photoUrls)
        .priceInBrl(price.getAmount())
        .build();
  }

  @JsonIgnore
  public boolean isPartialUpdate() {
    return ObjectUtils.allNull(id, name, description, photoUrls, price, metadata);
  }
}
