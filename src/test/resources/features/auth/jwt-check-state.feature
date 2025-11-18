Feature: Check if JWT is valid or not

  Background:
    Given the user exists

  Scenario: 1. Check state via domain service
    Given the user has a valid JWT
    When the check-state is attempted with the valid token
    Then the JWT is valid

  Scenario: 2. Check state with invalid JWT
    Given the user has an invalid JWT
    When the check-state is attempted with the invalid token
    Then the JWT is not valid
