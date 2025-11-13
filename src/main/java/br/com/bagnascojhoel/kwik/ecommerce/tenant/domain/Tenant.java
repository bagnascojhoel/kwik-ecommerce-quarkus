package br.com.bagnascojhoel.kwik.ecommerce.tenant.domain;

import br.com.bagnascojhoel.kwik.ecommerce.common.domain.identity.Author;
import br.com.bagnascojhoel.kwik.ecommerce.common.domain.identity.IdentityFactory;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "tenant", schema = "tenant")
@AllArgsConstructor
@Getter
public class Tenant extends PanacheEntityBase {

  private static final String DEFAULT_CREATOR = "admin";

  @EmbeddedId private final TenantId id;

  private final String name;

  @Embedded private final Author author;

  private Tenant() {
    this.id = null;
    this.name = null;
    this.author = null;
  }

  public static Tenant create(final TenantId id, final String name) {
    return new Tenant(id, name, IdentityFactory.getInstance().creator());
  }

  public static Tenant findById(final TenantId id) {
    return find("id", id).firstResult();
  }
}
