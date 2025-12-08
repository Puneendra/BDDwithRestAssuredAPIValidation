Feature: Location API Validation

  Scenario: Verify the location is added successfully by using post call in API
    Given create api request for adding a location
    When Do post call with query peramaters
    Then Verify status as OK
    And also verify scope as APP
