package br.com.bagnascojhoel.kwik.ecommerce.common.infra_driving.rest.api;

import io.quarkus.smallrye.openapi.OpenApiFilter;
import io.smallrye.openapi.internal.models.media.Schema;
import io.smallrye.openapi.internal.models.parameters.Parameter;
import java.util.List;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.media.Schema.SchemaType;
import org.eclipse.microprofile.openapi.models.parameters.Parameter.In;

@OpenApiFilter(OpenApiFilter.RunStage.BUILD)
public class OpenApiConfig implements OASFilter {

  @Override
  public Operation filterOperation(Operation operation) {
    for (DefaultHeader header : DefaultHeader.values()) {
      operation.addParameter(
          new Parameter()
              .in(In.HEADER)
              .name(header.name)
              .schema(new Schema().type(List.of(header.type)))
              .example(header.example));
    }

    return operation;
  }

  @AllArgsConstructor
  private enum DefaultHeader {
    ACTOR("x-actor", SchemaType.STRING, "admin");

    private final String name;
    private final SchemaType type;
    private final String example;
  }
}
