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
    Then Record with id <object ID> in table <Table> has to be validated
    Examples:
      | Table         | Object type  | object ID |
      | customers     | customer     | 120025    |
      | online_orders | online_order | 160002    |
      | products      | product      | 80016     |


  Scenario: Get orders for a specific customer
    Given Table online_orders is created
    When A search for all orders of a specific customer is performed
      | 120226 |
      | 120225 |
      | 120224 |
    Then Record with id 80127 in table products has to be validated


  Scenario: Generating custom object of type customer
    Given Table customers is created
    When Object of type customer is generated
      | 140056 | Winfred | Waelchi | Suite 168 8553 Brown Forge, Wunschfurt, MI 68118-5204 | Suite 289 | 1995 | com.github.javafaker.PhoneNumber@4760f169 | Lake Nicolle 98699 | 7002 |
      | 140057 | Winfred | Waelchi | Suite 168 8553 Brown Forge, Wunschfurt, MI 68118-5204 | Suite 289 | 1995 | com.github.javafaker.PhoneNumber@4760f169 | Lake Nicolle 98699 | 7002 |
      | 140058 | Winfred | Waelchi | Suite 168 8553 Brown Forge, Wunschfurt, MI 68118-5204 | Suite 289 | 1995 | com.github.javafaker.PhoneNumber@4760f169 | Lake Nicolle 98699 | 7002 |
    Then Record with id 140058 in table customers has to be validated


  Scenario: Generating custom object of type product
    Given Table products is created
    When Object of type product is generated
      | Intelligent Bronze Bag | Electronics | 9000 | 52 | 65 |
      | Sleek Iron Hat         | Electronics | 9001 | 52 | 35 |
      | Small Bronze Shoes     | Electronics | 9002 | 52 | 98 |
    Then Record with id 9002 in table products has to be validated

  Scenario: Generating custom object of type online_orders
    Given Table online_orders is created
    When Object of type online_orders is generated
      | 9000 | 145652 | boots, cheese | 52 | 65 | 2018-05-05T11:50:55  |
      | 9005 | 248898 | boots, cheese | 52 | 65 | 2018-05-05T11:50:55  |
      | 9010 | 458965 | boots, cheese | 52 | 65 | 2018-05-05T11:50:550 |

    Then Record with id 9002 in table products has to be validated

  Scenario: Updating an existing object
    Given Table products is created
    And  Record with id 80000 in table products has to be validated
    When An existing object of type with ID is updated
      | product  | 80000  | Sleek Iron Hat | Electronics | 90005 | 52 | 9002 |    |   |  |    |
      | customer | 140056 | 7452776        | 5656        | 9001  | 52 | 35   | 56 | 6 |  | 65 |
    Then Record with id 80000 in table products has to be validated


#  Scenario: Cleaning data and tables
##    Given All other tests are executed
#    When Data from tables is removed
#    And Tables are dropped
#    And Connection is closed
#    Then App is in idle