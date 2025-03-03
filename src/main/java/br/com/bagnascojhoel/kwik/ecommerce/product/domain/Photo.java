package br.com.bagnascojhoel.kwik.ecommerce.product.domain;

import br.com.bagnascojhoel.kwik.ecommerce.common.domain.identity.Author;
import br.com.bagnascojhoel.kwik.ecommerce.common.domain.identity.IdentityFactory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public class Photo {

  private final Long id;

  private final String url;

  private final ProductId productId;

  private final Author author;

  private Photo() {
    this.id = null;
    this.url = null;
    this.author = null;
    this.productId = null;
  }

  public static Photo create(String url) {
    return Photo.builder()
        .url(url)
        .author(IdentityFactory.getInstance().creator())
        .build();
  }
}
