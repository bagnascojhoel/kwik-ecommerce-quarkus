We use automated testing for all features. We know that e2e tests are the most powerful ones, but also the slower and harder to develop and maintain. So we use behavior integration testing to cover domain and application layer behavior; behavior end-to-end testing to validate happy path scenarios (usually run against the packaged or native app); and unit tests for fast, focused verifications.

The types of tests used are:
1. Behavior Integration Tests: prefered type of testing; the expected functionality is written on Given-When-Then using Gherking style; these focus on testing the application and domain layer and mocks everything outside it.
2. Behavior End-to-End Tests: cover only happy path of each feature; the expected functionality is written on Given-When-Then using Gherking style; nothing is mocked; they are ran against the native application, so no mocks;
3. Unit Tests: used for non-critic scenarios; should run fast.

We know that creating a powerful and concise set of fixtures is important, so we use the concept of object mothers (https://martinfowler.com/bliki/ObjectMother.html) for all tests. The fixtures must be built incrementally and be meaningful to the domain.

Test scenarios should be organized by FEATURE under `.feature` files. Each feature should have its own directory under `src/test/resources/features/<module>/`. There are two valid approaches to organizing integration and e2e scenarios: split files or co-locate with tags; both are supported.

1. Split approach (recommended for clear separation):

```
src/test/resources/features/auth/jwt-authentication/
├── integration.feature    # Integration test scenarios
└── e2e.feature            # End-to-end test scenarios
```

2. Co-located approach (used by `jwt-flow.feature`): integration and e2e scenarios can live in the same feature file and must be tagged with `@integration` and `@e2e` respectively. This approach is helpful when scenarios share rules and context and helps avoid duplication.

All scenarios within a `Rule:` block must be prefixed with an incremental integer starting from 1, followed by a period and space before the scenario name. Numbering should restart at 1 for each `Rule:` group. This also applies when mixing `@integration` and `@e2e` scenarios in the same file.

```
1. Scenario: Successful login
...

2. Scenario: Failed login
...
```

Whether you use split files or co-locate scenarios, the following rules must be observed:

- Use the tag `@integration` for tests that verify the application and domain layers and mock external dependencies. These tests should be fast and not start the entire application.
- Use the tag `@e2e` for end-to-end tests that run against the full application (often starting the actual web server), and must be executable from outside the application process.

The test scenarios using `@integration` must not start the whole application (light-weight; mock external systems). The `@e2e` scenarios start the full application and should use the `Given the application is running` step.
On the other hand, the @E2e scenarios will start the whole application and must be executable from outside the application. They will cover happy path scenarios and will be used to validate any generated native image of the project.

## JSON Response Matching in BDD Tests

For Behavior End-to-End Tests, when validating API responses, we use the `Then the response body matches` step to assert JSON response bodies. This step leverages JSONAssert (org.skyscreamer:jsonassert) in LENIENT mode, allowing for partial JSON matching. This means:

- The expected JSON in the feature file can contain only the fields you want to validate.
- Extra fields in the actual response are ignored.
- Array order is ignored.
- Whitespace and formatting differences are tolerated.

Example usage in a .feature file:

```
Then the response body matches
"""
{
  "valid": true
}
"""
```

This will pass even if the actual response includes additional fields like `{"valid": true, "timestamp": "2023-..."}`. For strict matching, the expected JSON must include all fields. This approach simplifies E2E test maintenance by focusing on key response attributes without brittleness from dynamic or extra data.

### Standardized Feature File Structure and Step Patterns (example)

We use a consistent structure for `.feature` files to improve readability and reuse. The `jwt-flow.feature` demonstrates a recommended structure that should be followed for new features. The key parts are:

- Top-level `Feature:` with a concise description
- `Rule:` blocks that group related scenarios
- Tagged scenarios (`@e2e` and/or `@integration`) using numbered `n. Scenario:` titles
- Reusable steps like `Given the application is running`, `Given a user exists with username "..." and a valid password`, `Given the request body`, and `Given the request headers` (for cookie or header tests), and `When the actor makes the POST request to` for API calls
- Validations using `Then the response status is`, `Then the response has cookies`, `Then the response body matches`, and other domain-specific asserts like `Then a JWT is issued`

Example snippet (based on `jwt-flow.feature`):

```
Feature: JWT Authentication Flow - Integration Tests

  Rule: Should generate a valid JWT with correct credentials

    @e2e
    Scenario: 1. Successful login via REST API
      Given the application is running
      And a user exists with username "kwik-admin" and a valid password
      Given the request body
        """
        {
          "username": "kwik-admin",
          "password": "correct-password"
        }
        """
      When the actor makes the POST request to "/api/auth/flows/jwt/login"
      Then the response status is "200"
      Then the response has cookies
        | JWT |  |
```

### Notes and Best Practices

- Prefer reusing existing step definitions instead of creating new ones when possible.
- Group related scenarios under `Rule` to keep the feature narrative clear.
- Use clear tags for scenario types. Tagging enables test runners and CI to filter which tests to run (e.g., `@e2e` for slower end-to-end suite vs `@integration` for lightweight CI tests).
- Use `Background:` for preset data that applies to multiple scenarios inside the same file; prefer `Given` steps for single-scenario preconditions.
- For cookie tests use `Given the request headers` with a single-column table that maps header names to values (e.g., `| Cookie | JWT=valid-token |`).
- When validating JWT behavior, use explicit steps for issuing/refreshing/checking states (e.g., `Then a JWT is issued`, `And the JWT is valid for 15 minutes`, `Then no JWT is issued`).

Following these conventions will keep test suites readable, maintainable, and easier to filter during CI runs.

## Assertion Library

The project exclusively uses AssertJ for all assertions in test code, including unit tests, integration tests, and end-to-end tests. Avoid using other assertion libraries such as JUnit assertions or Hamcrest to maintain consistency and leverage AssertJ's fluent API for better readability.
