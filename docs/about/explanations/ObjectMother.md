# Object Mother Pattern

## Definition and Overview

The Object Mother pattern is a testing design pattern that provides a centralized factory for creating reusable test fixtures (example objects). It addresses the challenge of setting up complex test data by offering "canned" objects that represent common scenarios, such as a standard user or product with predefined attributes.

In the context of the kwik-ecommerce-quarkus project, Object Mothers are used to build test fixtures incrementally and meaningfully for the domain, ensuring consistency across unit, integration, and end-to-end tests. This pattern originated from ThoughtWorks and is particularly useful for domain-driven design (DDD) applications where objects have rich business logic and relationships.

## Benefits and Use Cases

### Key Benefits
- **Reusability**: Share fixtures across multiple test classes, reducing duplication.
- **Readability**: Familiar, named objects (e.g., "kwikAdmin") make tests self-documenting.
- **Maintainability**: Centralized fixture creation simplifies updates.
- **Domain Alignment**: Fixtures reflect real business scenarios, improving test relevance.

### Use Cases in kwik-ecommerce-quarkus
- Creating authenticated users for authentication tests.
- Generating products with specific states (e.g., SHOWN, HIDDEN) for product management tests.
- Building tenant-scoped data for multi-tenancy validation.
- Supporting BDD scenarios in Cucumber tests where fixtures need to be predictable.

## Implementation Guide

### Basic Structure
1. Create a static class (e.g., `UserMother`) in the test package.
2. Define public static methods that return pre-built domain objects.
3. Use builders (e.g., Lombok `@Builder`) for fluent construction.
4. Place mothers in a dedicated package like `src/test/java/.../object_mother/`.

### Best Practices for Implementation
- Name methods descriptively (e.g., `kwikAdmin()` instead of `user1()`).
- Make objects immutable to prevent accidental mutations.
- Allow customization by returning builders or providing variants.
- Organize by domain module (e.g., `UserMother` in auth, `ProductMother` in product).

## Examples in Quarkus/Java

### Simple Example: UserMother
```java
package br.com.bagnascojhoel.kwik.ecommerce.auth.object_mother;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.Password;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.User;
import java.nio.charset.StandardCharsets;

public class UserMother {

  public static User kwikAdmin() {
    return User.builder()
        .username("kwik-admin")
        .email("kwikadmin@gmail.com")
        .securePassword(
            new Password("password".getBytes(StandardCharsets.UTF_8), "salt".getBytes()))
        .build();
  }

  public static User.Builder customUser() {
    return User.builder()
        .username("test-user")
        .email("test@example.com");
  }
}
```

### Usage in Test
```java
@QuarkusTest
public class AuthenticationTest {

  @Test
  void shouldLoginSuccessfully() {
    User admin = UserMother.kwikAdmin();
    // Test logic using the fixture
  }
}
```

### Advanced Example: ProductMother with Variants
```java
public class ProductMother {

  public static Product shownProduct(TenantId tenantId) {
    return Product.builder()
        .id(ProductId.of(1L))
        .name("Apple")
        .description("Winter fruit")
        .priceInBrl(BigDecimal.valueOf(5.00))
        .photosUrl(List.of("apple.jpg"))
        .state(ProductState.SHOWN)
        .tenantId(tenantId)
        .build();
  }

  public static Product hiddenProduct(TenantId tenantId) {
    return shownProduct(tenantId).withState(ProductState.HIDDEN);
  }
}
```

This example demonstrates incremental building and state variations, aligning with the project's enum-based product states.

## Drawbacks and Best Practices

### Drawbacks
- **Tight Coupling**: Tests depend on fixture data; changes require updating multiple tests.
- **Maintenance Overhead**: Evolving domain classes may break mothers.
- **Over-Abstraction**: Simple tests might not need complex fixtures.

### Best Practices
- Tweak existing fixtures instead of creating new ones for minor variations.
- Use builders for customization to avoid proliferation.
- Regularly refactor mothers as the domain evolves.
- Combine with other patterns like Test Data Builders for flexibility.
- Ensure fixtures are meaningful to the domain, as per project guidelines.

For further reading, see Martin Fowler's original article: https://martinfowler.com/bliki/ObjectMother.html.