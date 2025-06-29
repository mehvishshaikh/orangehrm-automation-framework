@OrangeHRMLogin
Feature: OrangeHRM Initial Test Suite

  Scenario: Login to the application
    Given User launches the application URL
    And Enter username and password
    Then Click on login button
    Then verify the title of the current page after login
    Then verify that the login was successful
    
  Scenario: Validate Admin Section
    Then click on Admin section
    Then verify that the Admin section is displayed