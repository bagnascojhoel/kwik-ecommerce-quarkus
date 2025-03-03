package br.com.bagnascojhoel.kwik.ecommerce.product.domain;

import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

  Product insert(@Nonnull final Product product);

  Product update(@Nonnull final Product product);

  Optional<Product> get(@Nonnull final ProductId productId);

  List<Product> onState(@Nonnull final ProductState productState);

  List<Product> getAll();

}
