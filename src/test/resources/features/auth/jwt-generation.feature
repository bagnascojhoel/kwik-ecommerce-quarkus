Feature: Generate a valid JWT with correct credentials

  Background:
    Given the user exists

  Scenario: 1. Successful login with username
    When the login is attempted with correct username and password
    Then a JWT is issued
    And the JWT is valid for 15 minutes

  Scenario: 2. Successful login with email
    When the login is attempted with correct email and password
    Then a JWT is issued
    And the JWT is valid for 15 minutes

  Scenario: 3. Successful login with valid credentials
    When the login is attempted with correct username and password
    Then a JWT is issued
    And the JWT is valid for 15 minutes

  Scenario: 4. Failed login with invalid credentials
    When the login is attempted with incorrect password
    Then no JWT is issued
    And an authentication error is returned
