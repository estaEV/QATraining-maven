package com.estafet.learning.sprint7;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.estafet.learning.sprint7.Globals.*;
import static org.junit.Assert.*;

public class StorePlatformStepDefinition {

    ConnectComponent comp = null;
    String resultSetTable = null;

    @Given("A connection is open")
    public void a_connection_is_open() {
        comp = new ConnectComponent();
        assertNotNull(comp);
    }

    @When("^Tables? (\\w+) (?:are|is) created$")
    //@When("Table {string} is created")
    public void tablesAreCreated(String table) throws SQLException {
        tableNameHasToBePresentIntoTheDB(table);
        if ((resultSetTable == null || (!resultSetTable.equals(table)))) {
            try {
                comp.createTables(tablesToWorkWith3);
            } catch (Exception e) {
                ;;
            }
            tableNameHasToBePresentIntoTheDB(table);
        }
        assertEquals(table, resultSetTable);
    }


    @Then("^(\\w+) (?:has|is) to be present into the DB$")
    public void tableNameHasToBePresentIntoTheDB(String table) throws SQLException {
        StringBuilder query = new StringBuilder();

        query
                .append("SHOW TABLES LIKE ")
                .append(System.getProperty("line.separator"))
                .append("'").append(table).append("'")
                //.replace(0, 0, "").replace(table.length(), table.length(), "'");
                .append(";");

        try (PreparedStatement preparedStatement = connection.
                prepareStatement(query.toString());) {

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    resultSetTable = resultSet.getString(1);
                }
            }
        }
    }


    @When("Data from tables is removed")
    public void dataFromTablesIsRemoved() throws SQLException {
        comp.truncateTable(tablesToWorkWith);
    }

    @And("Tables are dropped")
    public void tablesAreDropped() throws SQLException {
        comp.deleteTables(tablesToWorkWith3);
    }

    @And("Connection is closed")
    public void connectionIsClosed() throws SQLException {
        ConnectComponent.closeConnection();
    }

    @Then("App is in idle")
    public void appIsInIdle() throws SQLException {
        assertTrue(connection.isClosed());
    }

    @Given("All other tests are executed")
    public void allOtherTestsAreExecuted() {
        assertNotNull(resultSetTable);
    }

    @When("^A search in table (\\w+) for an object of type ([a-zA-Z_]+) with ID (\\d+) is conducted$")
    public void aSearchForAnObjectsConducted(String tableName, String objectType, String objectId) throws SQLException {
        String columnId = null;
        String adapter = "";


        switch(tableName) {
            case "customers":
                columnId = "customer_number";
                break;
            case "products":
                columnId = "product_code";
                //objectId = "\"" + objectId + "\"";
                adapter = "\"";
                break;
            case "online_orders":
                columnId = "order_number";
                break;
        }

        StringBuilder query = new StringBuilder();
        query
                .append("SELECT * FROM " + tableName + " " +
                        System.getProperty("line.separator") +
                        "WHERE " + columnId + " = " + adapter + objectId + adapter + ";");


        try (PreparedStatement preparedStatement = connection.
                prepareStatement(query.toString());) {

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    if ("product".equals(objectType)) {
                        String result2 = null;
                        result2 = resultSet.getString(3);
                        assertEquals(objectId, result2);
                    }
                    else {
                        int result = 0;
                        result = resultSet.getInt(1);
                        assertEquals(Integer.parseInt(objectId), result);
                    }
                }
            }
        }
    }
}
