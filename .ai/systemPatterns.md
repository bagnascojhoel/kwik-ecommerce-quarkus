# System Patterns

## Architectural Style: Ports & Adapters (Hexagonal Architecture)

### Core Principle

The domain model is at the center, completely isolated from external concerns. All infrastructure adapts to the domain, never the reverse.

## Module Structure

### Package Organization

```
br.com.bagnascojhoel.kwik.ecommerce/
├── auth/                    # Authentication & User Management
├── product/                 # Product Catalog Management
├── tenant/                  # Multi-tenancy Support
├── common/                  # Shared Domain Concepts
└── infra_shared/           # Shared Infrastructure
```

### Layer Architecture (Per Module)

Each module follows this structure:

```
module/
├── domain/              # Core business logic
│   ├── entities         # Product, User, etc.
│   ├── value objects    # ProductId, ProductState, etc.
│   ├── repositories     # Interfaces only
│   ├── services         # Domain services
│   └── exceptions       # Business exceptions
├── application/         # Use case orchestration
│   └── *UseCases       # Application services
├── infra_driven/       # Technical implementations
│   ├── database/       # JPA entities, repositories
│   └── external/       # REST clients, etc.
└── infra_driving/      # Entry points
    └── rest/           # REST controllers
```

### Dependency Rules

**Allowed Dependencies:**

```
infra_driving → application → domain
infra_driven → domain
infra_driven ↔ infra_shared
infra_driven ↔ infra_shared
```

**Prohibited:**
- Domain → anything except JDK
- Domain → Quarkus, Jakarta, or any framework
- Application → infra_driving or infra_driven directly

## Key Patterns

### 1. Repository Pattern

**Purpose:** Abstract data persistence

**Domain Side:**

```java
public interface ProductRepository {
    Product insert(Product product);
    Product update(Product product);
    Optional<Product> get(ProductId productId);
    List<Product> getAll();
}
```

**Infrastructure Side:**

```java
@ApplicationScoped
public class PanacheProductRepository 
    implements ProductRepository, PanacheTenantRepositoryBase {
    // Panache-specific implementation
}
```

### 2. Entity Mapping Pattern

**Purpose:** Separate domain models from persistence models

**Domain Entity:**

```java
@Getter
@Builder
public class Product {
    private final ProductId id;
    private final ProductState state;
    private final String name;
    // ... business logic
}
```

**Persistence Entity:**

```java
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id @GeneratedValue
    private Long id;
    
    public static ProductEntity from(TenantId tid, Product product);
    public Product to();
}
```

### 3. Application Service Pattern

**Purpose:** Orchestrate use cases, manage transactions

```java
@ApplicationScoped
@AllArgsConstructor
public class ProductManagementUseCases {
    private final ProductRepository productRepository;
    private final TenantContextHolder tenantContextHolder;
    
    @Transactional
    public void createProduct(TenantId tenantId, SaveProductCommand cmd) {
        tenantContextHolder.loadTenant(tenantId);
        Product product = Product.create(cmd);
        productRepository.insert(product);
    }
}
```

### 4. REST Adapter Pattern

**Purpose:** Translate HTTP to domain calls

**API Definition (Interface):**

```java
@Path("/api/tenants/{tenantId}/management/products")
public interface ProductManagementRestApi {
    @POST
    JsonApiFeedback createProduct(
        @PathParam("tenantId") String tenantId, 
        JsonProduct product);
}
```

**Adapter (Implementation):**

```java
@AllArgsConstructor
public class ProductManagementRestAdapter 
    implements ProductManagementRestApi {
    
    private final ProductManagementUseCases useCases;
    
    @Override
    public JsonApiFeedback createProduct(String tid, JsonProduct json) {
        SaveProductCommand cmd = json.toSaveCommand();
        useCases.createProduct(TenantId.of(tid), cmd);
        return JsonApiFeedback.ofCreated();
    }
}
```

### 5. Command Pattern

**Purpose:** Encapsulate operation parameters

```java
@Builder
@Getter
public class SaveProductCommand {
    private final String name;
    private final String description;
    private final BigDecimal priceInBrl;
    private final List<String> photosUrl;
    private final ProductState productState;
}
```

### 6. Multi-Tenancy Pattern

**Purpose:** Data isolation at database level

```java
public interface PanacheTenantRepositoryBase<Entity, Id> 
    extends PanacheRepositoryBase<Entity, Id> {
    
    default Optional<Entity> findByIdOptional(TenantId tid, Id id) {
        return find("tenantId = ?1 and id = ?2", tid, id)
            .firstResultOptional();
    }
}
```

### 7. Identity Factory Pattern

**Purpose:** Track entity creators/modifiers

```java
public class IdentityFactory {
    private static IdentityFactory instance;
    
    public Author creator() {
        // Returns current actor as Author
    }
}
```

### 8. Value Object with Factory Pattern

**Purpose:** Type-safe identifiers

```java
@Value
public class ProductId {
    Long value;
    
    public static ProductId of(Long value) {
        return new ProductId(value);
    }
}
```

## Component Relationships

### Authentication Flow

```
LoginRequest → JwtRestAdapter 
  → JwtFlowApplicationService 
  → UserRepository 
  → UserAuthenticationService 
  → JWT Response
```

### Product Creation Flow

```
JsonProduct → ProductManagementRestAdapter
  → ProductManagementUseCases
  → TenantContextHolder (set tenant)
  → Product.create(command)
  → ProductRepository.insert()
  → ProductEntity persistence
```

### Customer Product View Flow

```
GET /customer/products → CustomerProductRestAdapter
  → ProductManagementUseCases.findAllProductsToShowCustomers()
  → ProductRepository.onState(SHOWN)
  → Filter by state in database
  → Return JsonProductCollection
```

## Design Decisions

### 1. Immutable Domain Objects

- Use Lombok `@Value` and `@Builder`
- State changes create new instances (`.withState()`)
- Prevents accidental mutations

### 2. Panache for Persistence

- Active Record pattern via PanacheEntityBase
- Repository pattern via PanacheRepository
- Simplifies common queries
- Tenant-aware base repository

### 3. Enum for Product State

- Type-safe state management
- Prevents invalid states
- Easy to extend

### 4. Separate API and Adapter

- Interface defines contract (OpenAPI generation)
- Implementation handles translation
- Clear separation of HTTP concerns

### 5. Command Objects for Input

- Decouples JSON from domain
- Validates at boundary
- Immutable transfer objects

### 6. Application Services for Transactions

- Single transaction boundary
- Coordinates multiple repositories
- No transactions in domain

### 7. Builder Pattern for Complex Objects

- Fluent API for construction
- Handles optional fields elegantly
- Works with immutable objects

## Error Handling Strategy

### Domain Exceptions

```java
public class ProductNotFoundException 
    extends AbstractResourceNotFoundException {
    // Thrown in domain/application
}
```

### Global Exception Handling

- Maps domain exceptions to HTTP status codes
- Returns structured error responses
- Includes validation details

### Validation Points

1. **Input Layer**: Basic format validation (JSON)
2. **Domain Layer**: Business rule validation (constructor/methods)
3. **Application Layer**: Orchestration validation (resource existence)

## Testing Strategy

### Unit Tests

- Domain logic in isolation
- Mock repository interfaces
- BDD style (given/when/then)

### Integration Tests

- Test repository implementations
- Use real database (in-memory SQLite)
- Verify entity mapping

### BDD/Acceptance Tests

- Gherkin feature files
- HTTP API testing with REST Assured
- End-to-end scenarios
- Located in `src/test/resources/user-acceptance-testing/`

## Code Quality Patterns

### Lombok Annotations

- `@Value`: Immutable value objects
- `@Builder`: Fluent construction
- `@AllArgsConstructor`: Constructor injection
- `@Getter`: Read-only access
- `@Slf4j`: Logging

### Jakarta Annotations

- `@ApplicationScoped`: CDI beans
- `@Transactional`: Transaction boundaries
- `@Entity`, `@Table`: JPA mapping
- `@Path`, `@GET`, `@POST`: REST endpoints

### Validation

- Jakarta validation in domain (`@NotBlank`, `@Positive`)
- Custom `Validatable` interface
- Fail-fast in constructors

