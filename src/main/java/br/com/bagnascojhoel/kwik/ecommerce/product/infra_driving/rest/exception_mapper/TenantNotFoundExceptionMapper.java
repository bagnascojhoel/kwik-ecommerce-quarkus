package br.com.bagnascojhoel.kwik.ecommerce.product.infra_driving.rest.exception_mapper;

import br.com.bagnascojhoel.kwik.ecommerce.common.domain.TenantNotFoundException;
import br.com.bagnascojhoel.kwik.ecommerce.product.infra_driving.rest.api.JsonApiFeedbackImpl;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
public class TenantNotFoundExceptionMapper implements ExceptionMapper<TenantNotFoundException> {

  @Override
  public Response toResponse(TenantNotFoundException exception) {
    log.atDebug().log("tenant not found, tenant-id={}", exception.getTenantId().getBusinessId());
    return Response.status(Response.Status.NOT_FOUND)
        .entity(JsonApiFeedbackImpl.create(exception, Status.NOT_FOUND))
        .build();
  }
}
