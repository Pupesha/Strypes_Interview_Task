Feature: Strypes.eu home page testing

  Background:
    Given User is at home page

  Scenario: Verify search field function with invalid search term
    Given Search form is open
    When User enters invalid search term
    Then No results should be found

  Scenario: Verify if Main menu links are valid
    Then Validate Main menu links

  Scenario: Verify if Main menu links are leading to correct pages
    Then Verify Main menu links

  Scenario: Verify if Main menu top level links are changing their color on hover
    Then Verify Main menu top links hover color

  Scenario: Verify if Back To Top button is hidden when user is at the top of the page
    Given User is at top of the page
    Then Back To Top button is hidden

  Scenario: Verify if Back To Top button is displayed when user is not at the top of the page
    Given User is not at top of the page
    Then Back To Top button is displayed

  Scenario: User attempts newsletter subscription with valid not subscribed email
    Given User enters valid not subscribed email
    And User checks the Privacy policy checkbox
    When User clicks Subscribe button
    Then User is subscribed
    And Correct welcome message is displayed



