# Running tests locally

## Purpose

Provide quick, copy-pastable commands and tips to run different test suites in this project.

## Quick commands

### Run unit and component tests (fast)

```bash
./gradlew test
```

This runs the default unit + component tests. It excludes Cucumber and e2e suites.

### Run Cucumber feature tests

```bash
# run all cucumber feature tests
./gradlew testCucumber

# run a single feature file
./gradlew testCucumber -PfeatureFile=src/test/resources/features/auth/integration.feature

# run a single scenario by name
./gradlew testCucumber -PscenarioName="1. Successful login"
```

Notes:

- Use `-PfeatureFile` to scope to a single `.feature` file path.
- Use `-PscenarioName` to run a single scenario (prefix scenario names with rule numbers if
  present).

### Run End-to-End tests

```bash
# build the app (quarkusBuild) and run e2e tests
./gradlew testE2e

# run a specific e2e feature or scenario
./gradlew testE2e -PfeatureFile=src/test/resources/features/auth/e2e.feature
./gradlew testE2e -PscenarioName="1. Successful login"
```

Notes:

- `testE2e` depends on a built application (`quarkusBuild`) and runs integration tests against the
  packaged app. Expect longer run times.

## Best practices

- When developing, run unit tests frequently and use `testCucumber` scoped with `-PfeatureFile`
  during acceptance-test development.
- Use the project TestProfile for Cucumber integration tests to enable mocks (`@Alternative`) and
  avoid expensive subsystems.
- Prefer JSONAssert in LENIENT mode for HTTP body comparisons in e2e tests.

## Troubleshooting

- If tests fail due to DB migration or external services, check whether the TestProfile is enabled
  for that suite. If not, enable a profile that provides mock implementations.
- To debug a single failing test, run Gradle with `--no-daemon --info` and use your IDE test runner
  for step debugging.

## CI tips

- Run unit tests on every PR.
- Run Cucumber integration tests on a scheduled job or on feature branches.
- Run e2e tests on the release pipeline or nightly builds to validate the full application.
