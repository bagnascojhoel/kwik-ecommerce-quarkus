package br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driving.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Auth")
@Path("/api/auth/flows/jwt")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AuthFlowJwtRestApi {

  @Operation(summary = "Login")
  @POST
  @Path("/login")
  Response login(JsonJwtLogin jsonJwtLogin);

  @Operation(summary = "Refresh JWT")
  @POST
  @Path("/refresh")
  Response refresh(@CookieParam("jwt") String token);

  @Operation(summary = "Check JWT state")
  @POST
  @Path("/check-state")
  Response checkState(@CookieParam("jwt") String token);
}
