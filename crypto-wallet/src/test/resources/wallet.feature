Feature: validating crypto wallet features
  Scenario: reading a specific wallet
    Given a client wallet file
    When we parse this file
    And call Coincap API
    Then get the final result