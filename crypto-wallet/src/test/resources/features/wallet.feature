Feature: validating crypto wallet features
  Scenario: reading a specific wallet
    Given a client wallet file
    And we have the Coincap API working fine
    When we initiallize the wallet processing
    Then get the final results as expected