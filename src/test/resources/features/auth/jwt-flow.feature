Feature: JWT Authentication for Kwik

  Background:
    Given current user is the Kwik Admin

  Scenario: Admin user login with default password
    Given no prior user register is made
    When the admin user tries to login with default password
    Then JWT is generated
