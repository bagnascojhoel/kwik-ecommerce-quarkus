package br.com.bagnascojhoel.kwik.ecommerce.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
public class ProductId implements Boxed<Long> {

  @Column(name = "product_id")
  private final long id;

  @Override
  public Long value() {
    return id;
  }

}
