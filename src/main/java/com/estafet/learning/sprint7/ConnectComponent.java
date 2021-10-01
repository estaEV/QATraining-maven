package com.estafet.learning.sprint7;

import org.springframework.util.StopWatch;


import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.estafet.learning.sprint7.Globals.connection;
import static com.estafet.learning.sprint7.Globals.tablesToWorkWith;
import static java.lang.String.valueOf;

public class ConnectComponent {

    public static Connection openConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/store_platform?user=admin2&password=admin2&serverTimezone=UTC");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        }
        //System.out.printf("connection is : ", connection);
        return connection;
    }


    public void createTables(String[][] tablesToCreate) throws SQLException {

        for (int i = 0; i < tablesToCreate.length; i++) {

            StringBuilder params = new StringBuilder("");
            for (int j = 1; j < tablesToCreate[i].length; j++) {
                if (j != tablesToCreate[i].length - 1) {
                    params.append("$col").append(valueOf(j)).append(", ");
                } else {
                    params.append("$col").append(valueOf(j));
                }
            }

            String strQuery =
                    "CREATE TABLE $tableName "
                            + "(" + params + "); ";
            for (int j = 1; j < tablesToCreate[i].length; j++) {
                strQuery = strQuery
                        .replace("$col" + j, tablesToCreate[i][j]);
            }
            strQuery = strQuery
                    .replace("$tableName", tablesToCreate[i][0]);

            try (PreparedStatement preparedStatement = connection.
                    prepareStatement(strQuery);) {

                preparedStatement.executeUpdate();
            }
        }
    }

    public void deleteTables(String[][] tablesToDelete) throws SQLException {
        for (int i = 0; i < tablesToDelete.length; i++) {
            // Vulnerable to sql injection but still
            String strQuery = "DROP TABLE IF EXISTS "
                    + "$tableName;";
            String query = strQuery.replace("$tableName", tablesToDelete[i][0]);

            try (PreparedStatement preparedStatement = connection.
                    prepareStatement(query);) {
                preparedStatement.executeUpdate();
            }
        }
    }


    public void truncateTable(String[] tablesToDelete) throws SQLException {

        for (int i = 0; i < tablesToDelete.length; i++) {

            String strQuery =
                    "TRUNCATE $tableName ";

            String query = strQuery
                    .replace("$tableName", tablesToDelete[i]);

            try (PreparedStatement preparedStatement = connection.
                    prepareStatement(query)) {
                preparedStatement.executeUpdate();
            }
        }
    }



    public void insertStudentsData(String[] tablesToWorkWith, RandomGenerator randData) throws SQLException {

        List<Customer> listCust = new ArrayList<>();
        listCust = randData.getCustomersList();

        for (int i = 0; i < listCust.size(); i++) {
            listCust.get(i);

            String strQuery =
                    "INSERT INTO $tableName "
                            + "(customer_number, first_name, last_name, address_line1, address_line2,"
                            + " year, phone, city, postcode) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            String query = strQuery
                    .replace("$tableName", tablesToWorkWith[0]);

            try (PreparedStatement preparedStatement = connection.
                    prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setInt(1, listCust.get(i).getCustomer_number());
                preparedStatement.setString(2, listCust.get(i).getFirst_name());
                preparedStatement.setString(3, listCust.get(i).getLast_name());
                preparedStatement.setString(4, listCust.get(i).getAddress_line1());
                preparedStatement.setString(5, listCust.get(i).getAddress_line2());
                preparedStatement.setInt(6, listCust.get(i).getYear());
                preparedStatement.setString(7, listCust.get(i).getPhone());
                preparedStatement.setString(8, listCust.get(i).getCity());
                preparedStatement.setString(9, listCust.get(i).getPostcode());
                preparedStatement.executeUpdate();
            }
        }
    }

    public void insertProductsData(String[] tablesToWorkWith, RandomGenerator randData) throws SQLException {

        List<Product> listProd = new ArrayList<>();
        listProd = randData.getProductsList();

        for (int i = 0; i < listProd.size(); i++) {
            listProd.get(i);

            String strQuery =
                    "INSERT INTO $tableName "
                            + "(product_name, product_description, product_code, quantity, price) "
                            + "VALUES (?, ?, ?, ?, ?)";

            String query = strQuery
                    .replace("$tableName", tablesToWorkWith[1]);

            try (PreparedStatement preparedStatement = connection.
                    prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, listProd.get(i).getProduct_name());
                preparedStatement.setString(2, listProd.get(i).getProduct_description());
                preparedStatement.setString(3, listProd.get(i).getProduct_code());
                preparedStatement.setInt(4, listProd.get(i).getQuantity());
                preparedStatement.setDouble(5, listProd.get(i).getPrice());
                preparedStatement.executeUpdate();
            }
        }
    }
    

    public void insertOnlineOrdersData(String[] tablesToDelete, RandomGenerator randData) throws SQLException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Instant starts = Instant.now();

        List<OnlineOrder> listOnlineOrders = new ArrayList<>();
        listOnlineOrders = randData.getOnlineOrderList();

        for (int i = 0; i < listOnlineOrders.size(); i++) {

            listOnlineOrders.get(i);

            int numbersOfSubjectsStudied = listOnlineOrders.get(i).getListOfProducts().size();


            for (int j = 0; j < listOnlineOrders.get(i).getListOfProducts().size(); j++) {
                String strQuery =
                        "INSERT INTO $tableName "
                                + "(order_number, customer_number, total_price, date, product_code) "
                                + "VALUES (?, ?, ?, ?, ?)";

                String query = strQuery
                        .replace("$tableName", tablesToDelete[2]);

                try (PreparedStatement preparedStatement = connection.
                        prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                    preparedStatement.setInt(1, listOnlineOrders.get(i).getOrder_number());
                    preparedStatement.setInt(2, listOnlineOrders.get(i).getCustomer_number());
                    preparedStatement.setDouble(3, listOnlineOrders.get(i).getTotal_price());
                    preparedStatement.setString(4, String.valueOf(listOnlineOrders.get(i).getDate()));
                    preparedStatement.setString(5, listOnlineOrders.get(i)
                            .getListOfProducts().get(j)
                            .getProduct_code());
                    preparedStatement.executeUpdate();
                }
            }
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
        Instant ends = Instant.now();
        System.out.println(Duration.between(starts, ends));
    }


    public void updateObject(String table, String objectId, String sqlSet) {
        String columnId = null;
        String adapter = "";

        switch(table) {
            case "customers":
                columnId = "customer_number";
                break;
            case "products":
                columnId = "product_code";
                objectId = "\"" + objectId + "\"";
                adapter = "\"";
                break;
            case "online_orders":
                columnId = "order_number";
                break;
        }


        String strQuery =
                "UPDATE $table_name "
                        + sqlSet
                        + " WHERE " + columnId + " = " + objectId + ";";

        String query = strQuery
                .replace("$table_name", table);

        try (PreparedStatement preparedStatement = connection.
                prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            System.out.println("preparestatement: " + preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void mathAvgGrade(int studentsClassYear) throws SQLException {
        //studentsClassYear = 2018;
        double avgGrade = 0;
        String query =
                "SELECT AVG(grade) FROM college_book.gradebooks WHERE subjectId LIKE \"55598\" AND studentId IN (SELECT studentId FROM college_book.students WHERE classYear = ?);\n";
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(query);) {
            preparedStatement.setInt(1, studentsClassYear);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    System.out.println("resulseti is: " + resultSet);
                    System.out.println("resulseti is: " + preparedStatement);
                    avgGrade = resultSet.getDouble("AVG(grade)");
                }
            }
        }
        System.out.println("The AVG math grade for all students is: " + avgGrade);
    }

    public void deleteObject(String tableToDeleteFrom, String objectId ) throws SQLException {
        String columnId = null;
        String adapter = "";

        switch(tableToDeleteFrom) {
            case "customers":
                columnId = "customer_number";
                break;
            case "products":
                columnId = "product_code";
                objectId = "\"" + objectId + "\"";
                adapter = "\"";
                break;
            case "online_orders":
                columnId = "order_number";
                break;
        }


        StringBuilder query = new StringBuilder();

        query
                .append("DELETE FROM " + tableToDeleteFrom + " " +
                        System.getProperty("line.separator") +
                        "WHERE " + columnId + " = " + objectId + ";");

        try (PreparedStatement preparedStatement = connection.
                prepareStatement(String.valueOf(query));) {
            System.out.println("preparetStatement: " + preparedStatement);
            preparedStatement.executeUpdate();
            }
    }


    public static void closeConnection() throws SQLException {
            connection.close();
    }

}
