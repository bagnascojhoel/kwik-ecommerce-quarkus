package br.com.bagnascojhoel.kwik.ecommerce.tenant.infra_driving.rest.api;

import br.com.bagnascojhoel.kwik.ecommerce.common.infra_driving.rest.api.JsonApiFeedback;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Tag(name = "Kwik Back Office")
@Path("/api/tenants")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface TenantRestApi {

  @Operation(summary = "Creates a tenant")
  @POST
  JsonApiFeedback post(JsonTenant aTenant);

  @Operation(summary = "Gets all tenants")
  @GET
  List<JsonTenant> get();

  @Operation(summary = "Gets a tenant by its ID")
  @Path("/{id}")
  @GET
  RestResponse<JsonTenant> get(@PathParam("id") String aTenantId);
}
