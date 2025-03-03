package br.com.bagnascojhoel.kwik.ecommerce.product.infra_driven;

import br.com.bagnascojhoel.kwik.ecommerce.common.infra_driven.PanacheTenantRepositoryBase;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.Product;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.ProductId;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.ProductRepository;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.ProductState;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.TenantContextHolder;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class PanacheProductRepository implements
    ProductRepository,
    PanacheTenantRepositoryBase<ProductEntity, Long> {

  private final TenantContextHolder tenantContextHolder;

  @Override
  public Product insert(@Nonnull Product product) {
    this.persist(ProductEntity.from(tenantContextHolder.currentTenantId(), product));
    return product;
  }

  @Override
  public Product update(@Nonnull Product product) {
    this.getSession().merge(ProductEntity.from(tenantContextHolder.currentTenantId(), product));
    return product;
  }

  @Override
  public Optional<Product> get(@Nonnull ProductId productId) {
    return this.findByIdOptional(tenantContextHolder.currentTenantId(), productId.value())
        .map(ProductEntity::to);
  }

  @Override
  public List<Product> onState(@Nonnull ProductState productState) {
    return this.find("state = {}", productState).list().stream()
        .map(ProductEntity::to)
        .toList();
  }

  @Override
  public List<Product> getAll() {
    return this.findAll(tenantContextHolder.currentTenantId()).list().stream()
        .map(ProductEntity::to)
        .toList();
  }
}
