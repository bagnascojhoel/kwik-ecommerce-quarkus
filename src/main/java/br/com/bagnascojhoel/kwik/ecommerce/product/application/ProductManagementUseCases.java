package br.com.bagnascojhoel.kwik.ecommerce.product.application;

import br.com.bagnascojhoel.kwik.ecommerce.common.domain.TenantId;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.Product;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.ProductId;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.ProductNotFoundException;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.ProductRepository;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.ProductState;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.SaveProductCommand;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.TenantContextHolder;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ProductManagementUseCases {

  private final ProductRepository productRepository;

  private final TenantContextHolder tenantContextHolder;

  @Transactional
  public Product createProduct(
      @Nonnull final TenantId tenantId,
      @Nonnull @Valid final SaveProductCommand command
  ) {
    tenantContextHolder.loadTenant(tenantId);
    final Product product = Product.create(command);
    this.productRepository.insert(product);
    return product;
  }


  @Transactional
  public Product updateProduct(
      @Nonnull final TenantId tenantId,
      @Nonnull final ProductId productId,
      @Nonnull @Valid final SaveProductCommand command
  ) {
    tenantContextHolder.loadTenant(tenantId);
    final Product product = productRepository.get(productId)
        .map(p -> p.update(command))
        .orElseThrow(ProductNotFoundException::new);
    product.validate();
    this.productRepository.update(product);
    return product;
  }

  @Transactional
  public void updateProductState(
      @Nonnull final TenantId tenantId,
      @Nonnull final ProductId productId,
      @Nonnull final ProductState productState
  ) {
    tenantContextHolder.loadTenant(tenantId);
    Product product = productRepository.get(productId)
        .orElseThrow(ProductNotFoundException::new);
    this.productRepository.update(product.withState(productState));
  }

  @Transactional
  public Product getProductById(
      @Nonnull final TenantId tenantId,
      @Nonnull final ProductId productId
  ) {
    tenantContextHolder.loadTenant(tenantId);
    return this.productRepository.get(productId)
        .orElseThrow(ProductNotFoundException::new);
  }

  @Transactional
  public List<Product> findAllProducts(@Nonnull final TenantId tenantId) {
    tenantContextHolder.loadTenant(tenantId);
    return this.productRepository.getAll();
  }

  @Transactional
  public List<Product> findAllProductsToShowCustomers() {
    return this.productRepository.onState(ProductState.SHOWN);
  }
}
