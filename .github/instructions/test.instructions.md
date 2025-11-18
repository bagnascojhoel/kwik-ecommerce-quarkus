---

applyTo: '**/*'
description: 'Instructions for writing and maintaining tests in the project.'
-------------------------------------------------------------------------------

## Assertion Library

The project exclusively uses AssertJ for all assertions in test code. This applies to unit tests, integration tests, and end-to-end tests.

- Do not use other assertion libraries such as JUnit assertions (`assertEquals`, etc.) or Hamcrest.
- Leverage AssertJ's fluent API for readable and expressive assertions.

Example usage:

```java
import static org.assertj.core.api.Assertions.assertThat;

// In test methods
assertThat(actualResult).isEqualTo(expectedValue);
assertThat(list).hasSize(3).contains(element);
```

Ensure all new test code and modifications follow this standard to maintain consistency across the codebase.
