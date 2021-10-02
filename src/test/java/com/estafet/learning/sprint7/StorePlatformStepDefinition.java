package com.estafet.learning.sprint7;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
                ;
                ;
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


        switch (tableName) {
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
                    } else {
                        int result = 0;
                        result = resultSet.getInt(1);
                        assertEquals(Integer.parseInt(objectId), result);
                    }
                }
            }
        }
    }

    @When("A search for all orders of a specific customer is performed")
    public void aSearchForAllOrdersOfASpecificCustomerIsPerformed(List<String> customerNumber) throws SQLException {
        for (String s : customerNumber) {

            double ordersCount = 0;
            StringBuilder query = new StringBuilder();
            query
                    .append("SELECT COUNT(DISTINCT (order_number)) " +
                            System.getProperty("line.separator") +
                            "FROM online_orders " +
                            System.getProperty("line.separator") +
                            "WHERE customer_number = " + s + ";");

            try (PreparedStatement preparedStatement = connection.
                    prepareStatement(String.valueOf(query));) {

                try (ResultSet resultSet = preparedStatement.executeQuery();) {
                    while (resultSet.next()) {
                        ordersCount = resultSet.getInt("COUNT(DISTINCT (order_number))");
                    }
                }
            }
            System.out.printf("\nTotal number of orders for customer with ID: %2s is %1s", s, ordersCount);
        }
    }

    @When("Object of type product is generated")
    public void objectOfTypeProductIsGenerated(DataTable table) throws SQLException {
        List<List<String>> rows = table.asLists(String.class);

        RandomGenerator randData = new RandomGenerator();
        List<Product> prodList = new ArrayList<>();

        for (List<String> row : rows) {
            Product prodObj = new Product();

            prodObj.setProduct_code(row.get(0));
            prodObj.setProduct_description(row.get(1));
            prodObj.setProduct_code(row.get(2));
            prodObj.setQuantity(Integer.parseInt(row.get(3)));
            prodObj.setPrice(Double.parseDouble(row.get(3)));

            prodList.add(prodObj);
            randData.setProductsList(prodList);
            comp.insertProductsData(tablesToWorkWith, randData);
        }
    }


    @When("Object of type customer is generated")
    public void objectOfTypeCustomerIsGenerated(DataTable table) throws SQLException {
        List<List<String>> rows = table.asLists(String.class);

        RandomGenerator randData = new RandomGenerator();
        List<Customer> custList = new ArrayList<>();

        for (List<String> row : rows) {
            Customer obj = new Customer();

            obj.setCustomer_number(Integer.parseInt(row.get(0)));
            obj.setFirst_name(row.get(1));
            obj.setLast_name(row.get(2));
            obj.setAddress_line1(row.get(3));
            obj.setAddress_line2(row.get(4));
            obj.setYear(Integer.parseInt(row.get(5)));
            obj.setPhone(row.get(6));
            obj.setCity(row.get(7));
            obj.setPostcode(row.get(8));
            custList.add(obj);
            randData.setCustomersList(custList);
            comp.insertCustomersData(tablesToWorkWith, randData);
        }
    }


    @When("^Record with id (.+) in table (.+) has to be validated$")
    public void recordShouldBeValidated(String objectId, String tableName) throws SQLException {
        String columnId = null;
        String adapter = "";


        switch (tableName) {
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
                .append("SELECT " + columnId + " FROM " + tableName + " " +
                        System.getProperty("line.separator") +
                        "WHERE " + columnId + " = " + adapter + objectId + adapter + ";");

        try (PreparedStatement preparedStatement = connection.
                prepareStatement(query.toString());) {

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    if ("products".equals(tableName)) {
                        String result2 = null;
                        result2 = resultSet.getString(1);
                        assertEquals(objectId, result2);
                    } else {
                        int result = 0;
                        result = resultSet.getInt(1);
                        assertEquals(Integer.parseInt(objectId), result);
                    }
                }
            }
        }
    }

    @When("^An existing object of type with ID is updated$")
    public void anExistingObjectOfTypeTypeWithIDObjectIdIsUpdated(DataTable table) {

        List<List<String>> rows = table.asLists(String.class);
        String columnId = null;
        String adapter = "";
        int tableId = 99;
        String[] listOfColumns;
        String pattermatcher = "VARCHAR";
        for (List<String> row : rows) {

            switch (row.get(0)) {
                case "customer":
                    columnId = "customer_number";
                    tableId = 0;
                    break;
                case "product":
                    columnId = "product_code";
                    //objectId = "\"" + objectId + "\"";
                    adapter = "\"";
                    tableId = 1;
                    break;
                case "online_order":
                    columnId = "order_number";
                    tableId = 2;
                    break;
            }
            String adapter2 = "";
            String setStatement = "SET ";


            for (int i = 1; i < tablesToWorkWith3[tableId].length; i++) {
                int size = tablesToWorkWith3[tableId][i].length();

                if (tablesToWorkWith3[tableId][i].contains("VARCHAR")) {
                    adapter2 = "\"";
                } else {
                    adapter2 = "";
                }

                if (i == tablesToWorkWith3[tableId].length - 1) {
                    setStatement = setStatement.concat(tablesToWorkWith3[tableId][i].replaceAll("\\s.*", "") + " = " + adapter2 + row.get(i + 1) + adapter2 + " ");
                    //setStatement = setStatement.concat(tablesToWorkWith3[tableId][i].replaceAll("\\s.*", "") + " = " + adapter2 + row.get(i + 1) + adapter2 + ";");
                    break;
                } else {
                    setStatement = setStatement.concat(tablesToWorkWith3[tableId][i].replaceAll("\\s.*", "") + " = " + adapter2 + row.get(i + 1) + adapter2 + ", ");
                }
            }
            System.out.println("sqlme: " + setStatement);

            String strQuery =
                    "UPDATE $table_name "
                            + setStatement
                            + " WHERE " + columnId + " = " + adapter + row.get(1) + adapter + ";";

            String query = strQuery
                    .replace("$table_name", tablesToWorkWith3[tableId][0]);

            try (PreparedStatement preparedStatement = connection.
                    prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                System.out.println("preparestatement: " + preparedStatement);
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    @When("^Object of type online_orders is generated$")
    public void objectOfTypeOnline_ordersIsGenerated(DataTable table) throws SQLException {
        List<List<String>> rows = table.asLists(String.class);

        RandomGenerator randData = new RandomGenerator();
        List<OnlineOrder> ordersList = new ArrayList<>();

        for (List<String> row : rows) {
            OnlineOrder obj = new OnlineOrder();
            double totalPrice = 0;

            obj.setOrder_number(Integer.parseInt(row.get(0)));
            obj.setCustomer_number(Integer.parseInt(row.get(1)));

            List<String> asd = Arrays.asList(row.get(2).split(", "));
            for (String s : asd) {
                obj.setProduct_code(s);
            }

            obj.setQuantity(Integer.parseInt(row.get(3)));
            obj.setTotal_price(Double.parseDouble(row.get(4)));
            //TO DO: fix date
            obj.setDate(Date.valueOf(row.get(5)));
            randData.setOnlineOrderList(ordersList);
            comp.insertOnlineOrdersData(tablesToWorkWith, randData);

        }
    }
}

