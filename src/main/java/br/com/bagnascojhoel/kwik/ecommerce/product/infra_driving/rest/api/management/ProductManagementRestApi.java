package br.com.bagnascojhoel.kwik.ecommerce.product.infra_driving.rest.api.management;

import br.com.bagnascojhoel.kwik.ecommerce.common.infra_driving.rest.api.JsonApiFeedback;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/tenants/{tenantId}/management/products")
@Tags({@Tag(name = "Management")})
public interface ProductManagementRestApi {

  @GET
  JsonProductCollection getProducts(
      @PathParam("tenantId") @Schema(examples = "machados-barber") String tenantId);

  @GET
  @Path("/{prouductId}")
  JsonProduct getProduct(
      @PathParam("tenantId") String tenantId, @PathParam("productId") String productId);

  @POST
  JsonApiFeedback createProduct(
      @PathParam("tenantId") String tenantId, JsonProduct productResource);

  @PUT
  @Path("/{productId}")
  JsonApiFeedback updateFullProduct(
      @PathParam("tenantId") String tenantId,
      @PathParam("productId") Long productId,
      JsonProduct productResource);

  @Operation(description = "Only supports partial update of state.")
  @PATCH
  @Path("/{productId}")
  JsonApiFeedback updatePartialProduct(
      @PathParam("tenantId") String tenantId,
      @PathParam("productId") Long productId,
      @RequestBody(
              content =
                  @Content(
                      schema =
                          @Schema(
                              implementation = JsonProduct.class,
                              properties = @SchemaProperty(name = "state"))))
          JsonProduct productState);

  @DELETE
  @Path("/{productId}")
  JsonApiFeedback deleteProduct(
      @PathParam("tenantId") String tenantId, @PathParam("productId") String productId);
}
