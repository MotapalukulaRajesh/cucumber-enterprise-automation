Feature: Employee Management

  Background:
    Given User launches application

  @Regression
  Scenario Outline: Verify employee visibility

    Given Admin logs into OrangeHRM
    Given Employee "<FirstName>" is created using API
    And Admin searches employee
    Then Employee should be visible
    Examples:
      | FirstName |
      | Rajesh    |
      | John    |