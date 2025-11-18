Feature: Generate a new JWT when provided with a valid token

  Background:
    Given the user exists

  Scenario: 1. Refresh token via domain service
    Given the user has a valid JWT
    When the refresh is attempted with the valid token
    Then a JWT is issued
    And the JWT is valid for 15 minutes

  Scenario: 2. Refresh token with invalid JWT
    Given the user has an invalid JWT
    When the refresh is attempted with the invalid token
    Then no JWT is issued
    And an authentication error is returned
