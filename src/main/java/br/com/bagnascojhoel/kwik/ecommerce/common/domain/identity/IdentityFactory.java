package br.com.bagnascojhoel.kwik.ecommerce.common.domain.identity;

import br.com.bagnascojhoel.kwik.ecommerce.common.domain.RequestContext;
import io.quarkus.arc.Arc;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RequestScoped
@AllArgsConstructor
@Unremovable
public class IdentityFactory {

  @Inject private RequestContext requestContext;

  public static IdentityFactory getInstance() {
    try {
      return Arc.container().instance(IdentityFactory.class).get();
    } catch (Exception exception) {
      throw new IllegalStateException("could not get instance of IdentityFactory");
    }
  }

  public Author creator() {
    return Author.builder()
        .createdAt(LocalDateTime.now())
        .createdBy(requestContext.getActor())
        .modifiedAt(null)
        .modifiedBy(null)
        .build();
  }

  public Author modifier(@NonNull Author creator) {
    return creator.toBuilder()
        .modifiedBy(requestContext.getActor())
        .modifiedAt(LocalDateTime.now())
        .build();
  }
}
