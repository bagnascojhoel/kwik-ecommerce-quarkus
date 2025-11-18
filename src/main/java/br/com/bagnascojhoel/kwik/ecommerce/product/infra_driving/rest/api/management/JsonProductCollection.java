package br.com.bagnascojhoel.kwik.ecommerce.product.infra_driving.rest.api.management;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonProductCollection {

  private List<JsonProduct> products;

  public static JsonProductCollection of(List<JsonProduct> products) {
    return new JsonProductCollection(products);
  }
}
