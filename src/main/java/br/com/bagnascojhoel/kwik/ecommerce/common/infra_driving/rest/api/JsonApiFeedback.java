package br.com.bagnascojhoel.kwik.ecommerce.common.infra_driving.rest.api;

import jakarta.ws.rs.core.Response.Status;
import lombok.Getter;

public interface JsonApiFeedback {

  static JsonApiFeedback ofCreated() {
    return new DefaultJsonApiFeedback("Created", Status.CREATED.getStatusCode());
  }

  static JsonApiFeedback ofUpdated() {
    return new DefaultJsonApiFeedback("Updated", Status.OK.getStatusCode());
  }

  String getMessage();

  int getCode();

  Throwable getCause();

  @Getter
  class DefaultJsonApiFeedback implements JsonApiFeedback {

    private final String message;

    private final int code;

    private final Throwable cause;

    public DefaultJsonApiFeedback(String message, int code) {
      this.message = message;
      this.code = code;
      this.cause = null;
    }
  }

}
