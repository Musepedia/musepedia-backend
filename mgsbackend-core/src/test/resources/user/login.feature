Feature: User Login

  Scenario: New user login
    Given user open id not found
    When user login with open id "openid"
    Then new user is created and login success

  Scenario: Existing user login
    Given user open id can be found
    When user login with open id "openid"
    Then login success