package br.com.bagnascojhoel.kwik.ecommerce.product.infra_driving.rest.api;

import br.com.bagnascojhoel.kwik.ecommerce.product.domain.Currency;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonAmount {

  private BigDecimal amount;

  private Currency currencyCode;

  public static JsonAmount ofBrl(BigDecimal amount) {
    return new JsonAmount(amount, Currency.BRL);
  }
}
