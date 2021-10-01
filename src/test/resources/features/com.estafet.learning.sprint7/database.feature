Feature: General operations using the DB
  Free text here about the feature.

  Background:
    When A connection is open

  Scenario Outline: Creating new DB tables
  Free text here related to the scenario

    When Table <Table name> is created
    Then <Table name> has to be present into the DB

    Examples:
      | Table name    |
      | customers     |
      | products      |
      | online_orders |


  Scenario Outline: Search for a specific object in the DB

    Given Table <Table> is created
    When A search in table <Table> for an object of type <Object type> with ID <object ID> is conducted
    #Then The record should be validated
    Examples:
      | Table     | Object type | object ID |
      | customers | customer    | 120025    |
      | online_orders | online_order    | 160002    |
      | products | product    | 80016    |


#  Scenario: Cleaning data and tables
##    Given All other tests are executed
#    When Data from tables is removed
#    And Tables are dropped
#    And Connection is closed
#    Then App is in idle