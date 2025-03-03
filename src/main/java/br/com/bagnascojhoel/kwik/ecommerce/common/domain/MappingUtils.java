package br.com.bagnascojhoel.kwik.ecommerce.common.domain;

import br.com.bagnascojhoel.kwik.ecommerce.product.domain.Boxed;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MappingUtils {

  public static <T, R> R ifNonNull(final T value, final Function<T, R> mapperFunction) {
    return value == null ? null : mapperFunction.apply(value);
  }

  public static <T extends Boxed<R>, R> R ifNonNull(final T boxedValue) {
    return boxedValue == null ? null : boxedValue.value();
  }
}
