Feature: Submit Blood Pressure Reading
  As a user
  I want to submit my blood pressure reading
  So that I can see the calculated category and updated history

  Scenario: User submits a valid blood pressure reading
    Given a user provides a systolic value of 130 and a diastolic value of 86
    When the user submits the blood pressure form
    Then the system should calculate the blood pressure category
    And the category should be "High Blood Pressure (Hypertension Stage 1)"
    And the system should store the systolic history
    And the system should store the diastolic history
    And the system should return the "bp-telemetry" view


  Scenario: User submits a valid blood pressure reading
    Given a user provides a systolic value of 150 and a diastolic value of 170
    When the user submits the blood pressure form
    Then the system should calculate the blood pressure category
    And the stage2 category should be "High Blood Pressure (Hypertension Stage 2)"
    And the system should store the systolic history
    And the system should store the diastolic history
    And the system should return the "bp-telemetry" view