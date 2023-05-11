Feature: Strypes.eu home page testing

  Background:
    Given user is at home page

  Scenario: Verify search field function with missing search query
    Given search form is open
    When user enters missing phrase
    Then no results should be found

  Scenario: Verify if Main menu links are leading to correct pages
    Then verify Main menu links

  Scenario: Verify if Main menu top level links are changing their color on hover
    Then verify Main menu top links hover color

  Scenario: Verify if Back To Top button is hidden when user is at the top of the page
    When user is at top of the page
    Then back To Top button is hidden

  Scenario: Verify if Back To Top button is displayed when user is not at the top of the page
    When user is not at top of the page
    Then back To Top button is displayed

  Scenario: User attempts newsletter subscription with valid not subscribed email
    Given user enters valid not subscribed email
    And user checks the Privacy policy checkbox
    When user clicks Subscribe button
    Then user is subscribed
    And correct welcome message is displayed



