package br.com.bagnascojhoel.kwik.ecommerce.product.domain;

import static br.com.bagnascojhoel.kwik.ecommerce.product.domain.ProductState.HIDDEN;
import static java.util.Optional.ofNullable;

import br.com.bagnascojhoel.kwik.ecommerce.common.domain.identity.Author;
import br.com.bagnascojhoel.kwik.ecommerce.common.domain.identity.IdentityFactory;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import org.apache.commons.lang3.ObjectUtils;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Product implements Validatable {

  private final ProductId id;

  @With
  @NotNull
  private final ProductState state;

  @NotBlank(message = "product-name-not-blank")
  private final String name;

  @Size(max = 200, message = "product-description-size")
  private final String description;

  @NotNull(message = "product-price-not-null")
  @Positive(message = "product-price-positive")
  private final BigDecimal priceInBrl;

  private final List<Photo> photos;

  private final Author author;

  public Product(
      @Nonnull ProductId id,
      ProductState state,
      String name,
      @Nullable String description,
      BigDecimal priceInBrl,
      List<Photo> photos,
      @Nullable Author author
  ) {
    this.id = id;
    this.state = state == null ? HIDDEN : state;
    this.name = name;
    this.description = description;
    this.priceInBrl = priceInBrl;
    this.photos = photos;
    this.author = ObjectUtils.defaultIfNull(author, IdentityFactory.getInstance().creator());

    validate();
  }

  public static Product create(@Nonnull final SaveProductCommand command) {
    final Product.ProductBuilder builder = Product.builder();

    builder.id(null);
    builder.state(HIDDEN);
    Optional.ofNullable(command.getName()).ifPresent(builder::name);
    Optional.ofNullable(command.getDescription()).ifPresent(builder::description);
    Optional.ofNullable(command.getPhotosUrl()).ifPresent(builder::photoUrls);
    Optional.ofNullable(command.getPriceInBrl()).ifPresent(builder::priceInBrl);

    return builder.build();
  }

  public Product update(@Nonnull final SaveProductCommand saveProductCommand) {
    var builder = this.toBuilder();

    ofNullable(saveProductCommand.getPhotosUrl()).ifPresent(builder::photoUrls);
    ofNullable(saveProductCommand.getName()).ifPresent(builder::name);
    ofNullable(saveProductCommand.getDescription()).ifPresent(builder::description);
    ofNullable(saveProductCommand.getPriceInBrl()).ifPresent(builder::priceInBrl);
    ofNullable(saveProductCommand.getProductState()).ifPresent(builder::state);

    return builder.build();
  }

  public ProductBuilder toBuilder() {
    return (new ProductBuilder())
        .id(this.id)
        .state(this.state)
        .name(this.name)
        .description(this.description)
        .priceInBrl(this.priceInBrl)
        .photos(this.photos);
  }

  public boolean isNew() {
    return this.id == null;
  }

  public static class ProductBuilder {

    public ProductBuilder photoUrls(final List<String> photos) {
      this.photos = photos.stream().map(Photo::create).toList();
      return this;
    }
  }
}
