package br.com.bagnascojhoel.kwik.ecommerce.product.infra_driving.rest.exception_mapper;

import br.com.bagnascojhoel.kwik.ecommerce.infra_shared.ResponseStatus;
import br.com.bagnascojhoel.kwik.ecommerce.product.infra_driving.rest.api.JsonApiFeedbackImpl;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class ThrowableExceptionMapper implements ExceptionMapper<Throwable> {

  @Override
  public Response toResponse(Throwable exception) {
    log.atError().log("unexpected error", exception);
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(
            JsonApiFeedbackImpl.create(
                "unexpected error", ResponseStatus.INTERNAL_SERVER_ERROR.getStatus(), exception))
        .build();
  }
}
