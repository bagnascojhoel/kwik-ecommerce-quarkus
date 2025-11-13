package br.com.bagnascojhoel.kwik.ecommerce.product.infra_driven;

import br.com.bagnascojhoel.kwik.ecommerce.common.domain.identity.Author;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.Photo;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.ProductId;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "product_photo")
public class PhotoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private final Long id;

  @NotBlank private final String url;

  @Nonnull
  @ManyToOne
  @JoinColumn(name = "product_id")
  private final ProductEntity product;

  @Nonnull @Embedded private final Author author;

  private PhotoEntity() {
    this.id = null;
    this.url = null;
    this.product = null;
    this.author = null;
  }

  public static PhotoEntity from(ProductEntity productEntity, Photo photo) {
    return PhotoEntity.builder()
        .product(productEntity)
        .url(photo.getUrl())
        .author(photo.getAuthor())
        .id(photo.getId())
        .build();
  }

  public Photo to() {
    return new Photo(id, url, ProductId.of(product.getId()), author);
  }
}
