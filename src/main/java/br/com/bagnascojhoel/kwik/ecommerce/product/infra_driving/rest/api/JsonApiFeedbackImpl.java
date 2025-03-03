package br.com.bagnascojhoel.kwik.ecommerce.product.infra_driving.rest.api;

import br.com.bagnascojhoel.kwik.ecommerce.common.infra_driving.rest.api.JsonApiFeedback;
import br.com.bagnascojhoel.kwik.ecommerce.product.domain.KwikEcommerceException;
import io.quarkus.runtime.configuration.ConfigUtils;
import jakarta.ws.rs.core.Response.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonApiFeedbackImpl implements JsonApiFeedback {

  private String message;

  private int code;

  private Throwable cause;

  public static JsonApiFeedback create(KwikEcommerceException exception, Status status) {
    return new JsonApiFeedbackImpl(exception.getErrorCode(), status.getStatusCode(), null);
  }

  public static JsonApiFeedback create(String code, Status status) {
    return new JsonApiFeedbackImpl(code, status.getStatusCode(), null);
  }

  public static JsonApiFeedback create(String message, Status status, Throwable throwable) {
    if (ConfigUtils.isProfileActive("dev")) {
      return new JsonApiFeedbackImpl(throwable.getMessage(), status.getStatusCode(), throwable);
    }
    return new JsonApiFeedbackImpl(message, status.getStatusCode(), null);
  }
}
