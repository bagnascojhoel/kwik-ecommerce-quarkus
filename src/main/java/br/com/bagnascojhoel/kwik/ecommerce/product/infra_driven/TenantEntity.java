package br.com.bagnascojhoel.kwik.ecommerce.product.infra_driven;

import br.com.bagnascojhoel.kwik.ecommerce.common.domain.identity.Author;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "tenant", schema = "tenant")
@AllArgsConstructor
@Getter
public class TenantEntity extends PanacheEntityBase {

  private static final String DEFAULT_CREATOR = "admin";

  @Id
  @Column(name = "id")
  private final Long entityId;

  @Column(name = "business_id")
  private final String businessId;

  private final String name;

  @Embedded private final Author author;

  private TenantEntity() {
    this.entityId = null;
    this.businessId = null;
    this.name = null;
    this.author = null;
  }

  public static TenantEntity findByBusinessId(final String businessId) {
    return find("businessId", businessId).firstResult();
  }

  public static Optional<TenantEntity> findOptionalByBusinessId(final String businessId) {
    return find("businessId", businessId).firstResultOptional();
  }
}
