package br.com.bagnascojhoel.kwik.ecommerce.product.domain;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SaveProductCommand {

  private final String name;

  private final String description;

  private final BigDecimal priceInBrl;

  private final List<String> photosUrl;

  private final ProductState productState;
}
