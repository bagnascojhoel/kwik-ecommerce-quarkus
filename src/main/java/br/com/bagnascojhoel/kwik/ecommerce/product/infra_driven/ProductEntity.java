package br.com.bagnascojhoel.kwik.ecommerce.product.infra_driven;


import br.com.bagnascojhoel.kwik.ecommerce.common.domain.MappingUtils;
import br.com.bagnascojhoel.kwik.ecommerce.common.domain.TenantId;
import br.com.bagnascojhoel.kwik.ecommerce.common.domain.identity.Author;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.Product;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.ProductId;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.ProductState;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Builder(access = AccessLevel.PRIVATE, toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Enumerated(EnumType.STRING)
  private ProductState state;

  private String name;

  @Nullable
  private String description;

  @Column(name = "price_in_brl")
  private BigDecimal priceInBrl;

  @Embedded
  private TenantId tenantId;

  @Embedded
  private Author author;
  
  @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL})
  private List<PhotoEntity> photos;

  private ProductEntity() {
    this.id = null;
    this.state = null;
    this.name = null;
    this.description = null;
    this.priceInBrl = null;
    this.photos = null;
    this.tenantId = null;
    this.author = null;
  }

  public static ProductEntity from(final TenantId tenantId, final Product product) {
    ProductEntity productEntity = ProductEntity.builder()
        .id(MappingUtils.ifNonNull(product.getId()))
        .state(product.getState())
        .name(product.getName())
        .description(product.getDescription())
        .priceInBrl(product.getPriceInBrl())
        .tenantId(tenantId)
        .author(product.getAuthor())
        .build();

    productEntity.photos = product.getPhotos().stream()
        .map(photo -> PhotoEntity.from(productEntity, photo))
        .toList();

    return productEntity;
  }

  public Product to() {
    return new Product(
        ProductId.of(id),
        state,
        name,
        description,
        priceInBrl,
        Objects.nonNull(photos) ? photos.stream().map(PhotoEntity::to).toList() : new ArrayList<>(),
        author
    );
  }
}
