---

applyTo: '**/*.feature, **/*Step*.java'
---------------------------------------

# Behavior-Driven Development (BDD) Test Implementation Instructions

This document provides guidelines for implementing Behavior-Driven Development (BDD) tests in the project. BDD tests are essential for validating the behavior of the application from an end-user perspective and ensuring that all requirements are met.

## BDD Test Implementation Guidelines

 - `.feature` files MUST be placed in `src/test/resources/features/` under the specific module they belong to.
 - Each feature should have its own subdirectory containing scenarios. You can either split integration and e2e into separate files or co-locate them in a single `.feature` file and use tags to differentiate them. Both approaches are allowed:
   - Split approach: `integration.feature` and `e2e.feature`
   - Co-located approach: single file (e.g., `jwt-flow.feature`) with `@e2e` and `@integration` tags
- Step definition classes MUST be located at `bdd/steps`.
 - Most BDD tests must be integration tests, focusing on application and domain layers while mocking the rest.
- Happy path tests must be BDD e2e tests covering all layers.
- BDD tests must be written in Gherkin syntax, following the standard format of Given-When-Then.
 - All scenarios within a `Rule:` block MUST be prefixed with an incremental integer starting from 1, followed by a period and space (e.g., "1. Scenario: ...", "2. Scenario: ..."). Numbering should restart at 1 for each `Rule:` group. When using co-located integration and `@e2e` scenarios in the same file, apply the numbering rule per-`Rule` group.
- Each BDD test scenario should clearly describe a specific behavior or feature of the application.
 - Scenarios CAN have multiple Given, When, and Then steps to cover complex behaviors.
 - You MUST reuse existing steps whenever possible to maintain consistency and reduce redundancy. The following step templates are commonly used across API tests and should be reused:

  - `Given the application is running` — required for e2e scenarios that start the full app
  - `Given a user exists with username "<username>" and a valid password` — creates a user fixture
  - `Given the request body` — a triple-quoted JSON block for request payloads
  - `Given the request headers` — table form for headers, e.g. `| Cookie | JWT=valid-token |`
  - `When the login is attempted with correct username and password` — integration login step
  - `When the actor makes the POST request to "<endpoint>"` — used in e2e tests to perform actual HTTP calls
  - `Then the response status is "<code>"` — assert status
  - `Then the response has cookies` — table form to check cookies, e.g., `| JWT | |`
  - `Then the response body matches` — JSONAssert lenient match (used primarily in e2e to check responses)
  - Domain-specific assertions: `Then a JWT is issued`, `And the JWT is valid for 15 minutes`, `Then no JWT is issued`, `Then an authentication error is returned`, `Then the state is reported as valid`/`invalid`
 - New step definitions MUST be created in the appropriate step definition classes under `bdd/steps`, following the project's naming conventions. Prefer to extend or reuse existing steps in `bdd/steps` so maintainability is improved.
- BDD tests MUST be organized into feature files that correspond to specific features or modules of the application.
- Each feature file MUST include a clear and concise description of the feature being tested.
 - You MUST use `Background` sections in feature files when there are common preconditions shared by multiple scenarios in the same file. When using co-located `@e2e` and `@integration` scenarios, consider separating common preconditions appropriately in `Background` and use tags to ensure e2e-only steps start the application.

## Example structure and patterns (based on `jwt-flow.feature`)

1. Group related behavior using `Rule:` blocks.
2. Preferred tagging and numbering:

```
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

  @integration
  Scenario: 2. Successful login with valid credentials
    Given a user exists with username "kwik-admin" and a valid password
    When the login is attempted with correct username and password
    Then a JWT is issued
    And the JWT is valid for 15 minutes
```

3. Use `@e2e` and `@integration` tags for filtering in CI jobs and to control test environment setup.

Following this structure increases clarity and reduces friction for both writing and running BDD scenarios across integration and end-to-end suites.

