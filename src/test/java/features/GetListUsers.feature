Feature: Get all list users from the api

  Scenario: Verify the get api for the list users
    Given The url address of the API endpoint is accessed to obtain the list of users
    When The URL of the list of users is passed in the request
    Then The response code 200 is received
    Then The actual JSON response matches the expected Json response
