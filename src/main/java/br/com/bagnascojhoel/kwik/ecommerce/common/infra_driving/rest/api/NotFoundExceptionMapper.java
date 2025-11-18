package br.com.bagnascojhoel.kwik.ecommerce.common.infra_driving.rest.api;

import br.com.bagnascojhoel.kwik.ecommerce.common.domain.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

  @Override
  public Response toResponse(NotFoundException e) {
    return Response.status(Status.NOT_FOUND)
        .entity(
            JsonApiFeedback.DefaultJsonApiFeedback.builder()
                .code(404)
                .message(e.getMessage())
                .build())
        .build();
  }
}
