package br.com.bagnascojhoel.kwik.ecommerce.product.infra_driving.rest.exception_mapper;

import br.com.bagnascojhoel.kwik.ecommerce.infra_shared.ResponseStatus;
import br.com.bagnascojhoel.kwik.ecommerce.product.infra_driving.rest.api.JsonApiFeedbackImpl;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class UnsupportedOperationExceptionMapper
    implements ExceptionMapper<UnsupportedOperationException> {

  @Override
  public Response toResponse(UnsupportedOperationException exception) {
    log.atError().log("unsupported operation", exception);
    return Response.status(ResponseStatus.UNPROCESSABLE_ENTITY.getStatus())
        .entity(
            JsonApiFeedbackImpl.create(
                exception.getMessage(), ResponseStatus.UNPROCESSABLE_ENTITY.getStatus()))
        .build();
  }
}
