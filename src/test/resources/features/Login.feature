Feature: Login functionality

  Background:
    Given User launches application

  @Smoke
  Scenario: Login to OrangeHRM

  @Regression
  Scenario: Valid login
    When User logs in using excel row 1
    Then User should navigate to dashboard

  @smoke
  Scenario Outline: Login Validation
    When User enters username "<username>"
    And User enters password "<password>"
    Then User should see "<result>"

    Examples:
      | username | password      | result  |
      | Admin    | admin123      | Success |
      | Admin    | wrongPassword | Failure |
      | TestUser | welcome123    | Success |
