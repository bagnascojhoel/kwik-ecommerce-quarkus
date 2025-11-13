---
applyTo: '**/*.feature, **/*Step*.java'
---

# Behavior-Driven Development (BDD) Test Implementation Instructions
This document provides guidelines for implementing Behavior-Driven Development (BDD) tests in the project. BDD tests are essential for validating the behavior of the application from an end-user perspective and ensuring that all requirements are met.


## BDD Test Implementation Guidelines
- .feature files MUST be placed in `features/` directory under the specific module they belong to.
- Step definition classes MUST be located at `bdd/steps`.
- Most BDD tests must be integration tests, focusing on application and domain layers while mocking the rest.
- Happy path tests must be BDD e2e tests covering all layers.
- BDD tests must be written in Gherkin syntax, following the standard format of Given-When-Then.
- Each BDD test scenario should clearly describe a specific behavior or feature of the application.
- Scenarios CAN have multiple Given, When, and Then steps to cover complex behaviors.
- You MUST reuse existing steps whenever possible to maintain consistency and reduce redundancy.
- New step definitions MUST be created in the appropriate step definition classes, following the project's naming conventions
- BDD tests MUST be organized into feature files that correspond to specific features or modules of the application.
- Each feature file MUST include a clear and concise description of the feature being tested.
- You MUST use Background sections in feature files to define common preconditions for multiple scenarios.