package br.com.bagnascojhoel.kwik.ecommerce.product.infra_driving.rest.api.management;

import br.com.bagnascojhoel.kwik.ecommerce.common.domain.TenantId;
import br.com.bagnascojhoel.kwik.ecommerce.common.infra_driving.rest.api.JsonApiFeedback;
import br.com.bagnascojhoel.kwik.ecommerce.product.application.ProductManagementUseCases;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.ProductId;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductManagementRestAdapter implements ProductManagementRestApi {

  private final ProductManagementUseCases productManagementUseCases;

  @Override
  public JsonProductCollection getProducts(String tenantId) {
    return JsonProductCollection.of(
        productManagementUseCases.findAllProducts(TenantId.of(tenantId)).stream()
            .map(JsonProduct::create)
            .toList());
  }

  @Override
  public JsonProduct getProduct(String tenantId, String productId) {
    return null;
  }

  @Override
  public JsonApiFeedback createProduct(String tenantId, JsonProduct productResource) {
    productManagementUseCases.createProduct(TenantId.of(tenantId), productResource.toSaveCommand());
    return JsonApiFeedback.ofCreated();
  }

  @Override
  public JsonApiFeedback updateFullProduct(
      String tenantId, Long productId, JsonProduct productResource) {
    return null;
  }

  @Override
  public JsonApiFeedback updatePartialProduct(
      String tenantId, Long productId, JsonProduct productResource) {
    if (!productResource.isPartialUpdate()) {
      throw new UnsupportedOperationException();
    }
    productManagementUseCases.updateProductState(
        TenantId.of(tenantId), ProductId.of(productId), productResource.getState());
    return JsonApiFeedback.ofUpdated();
  }

  @Override
  public JsonApiFeedback deleteProduct(String tenantId, String productId) {
    return null;
  }
}
