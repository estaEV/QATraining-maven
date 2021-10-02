package com.estafet.learning.sprint7;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.estafet.learning.sprint7.Globals.*;

public class Main {

    public static void main(String[] args) throws SQLException, ParseException {
        Scanner sc = new Scanner(System.in);

        List<String> menu = new ArrayList<>();
        System.out.println("sprint7");
        menu.add("\n0. Exit.");
        menu.add("1. Create tables.");
        menu.add("2. Delete tables.");
        menu.add("3. Fill tables with data.");
        menu.add("4. Clean data from tables.");
        menu.add("5. Update customer data.");
        menu.add("6. Delete specific data from table.");

        boolean isRunning = true;

        ConnectComponent comp = new ConnectComponent();

        while (isRunning) {
            menu.forEach(option -> System.out.println(option));
            System.out.print("\nEnter the selected func(): ");
            int option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 0:
                    ConnectComponent.closeConnection();
                    isRunning = false;
                    break;
                case 1:
                    comp.createTables(tablesToWorkWith3);
                    break;
                case 2:
                    comp.deleteTables(tablesToWorkWith3);
                    break;
                case 3:
                    RandomGenerator randData = new RandomGenerator();
                    randData.generateMeSome();
                    comp.insertCustomersData(tablesToWorkWith, randData);
                    comp.insertProductsData(tablesToWorkWith, randData);
                    comp.insertOnlineOrdersData(tablesToWorkWith, randData);
                    System.out.println("im out");
                    break;
                case 4:
                    comp.truncateTable(tablesToWorkWith);
                    break;
                case 5:
                    System.out.print("Input the table to work with: ");
                    String table = sc.nextLine();
                    System.out.print("Enter objectId: ");
                    String customerId = sc.nextLine();
                    System.out.print("Input the new SET sql statement (SET column1 = value1, column2 = value2, ...): ");
                    String newSetStatement = sc.nextLine();
                    comp.updateObject(table, customerId, newSetStatement);
                    break;
                case 6:
                    String table2 = null;
                    System.out.println("\nInput the table to work with: ");
                    table2 = sc.nextLine();
                    System.out.println("\nEnter the ID of the object for deletion: ");
                    String objectId = sc.nextLine();
                    comp.deleteObject(table2, objectId);
                    break;
            }
        }
    }
}
