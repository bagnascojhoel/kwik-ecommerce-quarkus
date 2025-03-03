package br.com.bagnascojhoel.kwik.ecommerce.infra_shared;

import jakarta.ws.rs.core.Response.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseStatus {

  OK(Status.OK, "OK"),
  CREATED(Status.CREATED, "Created"),
  NO_CONTENT(Status.NO_CONTENT, "No Content"),
  BAD_REQUEST(Status.BAD_REQUEST, "Bad Request"),
  NOT_FOUND(Status.NOT_FOUND, "Not Found"),
  INTERNAL_SERVER_ERROR(Status.INTERNAL_SERVER_ERROR, "Internal Server Error"),
  UNAUTHORIZED(Status.UNAUTHORIZED, "Unauthorized"),
  FORBIDDEN(Status.FORBIDDEN, "Forbidden"),
  CONFLICT(Status.CONFLICT, "Conflict"),
  UNPROCESSABLE_ENTITY(Status.fromStatusCode(422), "Unprocessable Entity");

  private final Status status;
  private final String reason;

}
