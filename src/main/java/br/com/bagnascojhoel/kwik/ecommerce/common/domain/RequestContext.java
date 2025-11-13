package br.com.bagnascojhoel.kwik.ecommerce.common.domain;

import io.vertx.core.http.HttpServerRequest;
import jakarta.enterprise.context.RequestScoped;
import lombok.Getter;

@RequestScoped
@Getter
public class RequestContext {

  private final String actor;

  public RequestContext(HttpServerRequest request) {
    actor = request.headers().get("x-actor");
  }
}
