Feature: validating crypto wallet parallel processing
  Scenario: reading a big wallet csv file
    Given a client big wallet file
    When we initiallize the wallet parallel processing
    #TODO: And we can see X threads were created / processed as expected
    Then get the final results
