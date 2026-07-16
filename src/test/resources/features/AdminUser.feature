Feature: Admin User Management

  Background:
    Given User launches application
    Given Admin logs into OrangeHRM


  @Regression
  Scenario: Create or verify admin user exists
    Given User with dynamic username is created in Admin if not exists with password "TestPass@123"
    And Admin should be able to verify user in admin users list

  @Regression
  Scenario Outline: Search Multiple Users
    When Admin searches user "<username>"
    Then User should be displayed

    Examples:
      | username |
      | Test  |
      | Admin    |
