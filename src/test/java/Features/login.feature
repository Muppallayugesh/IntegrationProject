Feature: Login feature

  Background: Login into Facebook
    Given User is on Facebook login page

  Scenario: Login with valid credentials
    When User enters valid username and password
    And User clicks on login button
    Then User should be logged in successfully

  Scenario: Login with invalid credentials
    When User enters invalid username and password
    And User clicks on login button
    Then User should see an error message