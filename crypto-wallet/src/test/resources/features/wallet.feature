Feature: validating crypto wallet features
  Scenario: reading a specific wallet
    Given a client wallet file
    When we initiallize the wallet processing
    Then get the final results as expected

  # TODO: implement some other possible business errors scenarios
  # Error Scenario: reading an empty wallet
  # Error Scenario: getting error on wallet processing (Coincap is out)
  # Error Scenario: getting error on wallet processing (Coicap asset return no data)
  # Error Scenario: getting error on wallet processing (Coicap history return no data for some symbol/id)