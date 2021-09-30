package com.estafet.learning.sprint7;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.estafet.learning.sprint7.Globals.tablesToWorkWith;

public class Main {

    public static void main(String[] args) throws SQLException {

/*        Result result = JUnitCore.runClasses(InvoiceTestSuite.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }*/

        Scanner sc = new Scanner(System.in);
        List<String> menu = new ArrayList<>();
        System.out.println("sprint7");
        menu.add("\n0. Exit.");
        menu.add("1. Create tables.");
        menu.add("2. Delete tables.");
        menu.add("3. Fill tables with data.");
        menu.add("4. Clean data from tables.");

        boolean isRunning = true;

        ConnectComponent comp = new ConnectComponent();

        while (isRunning) {
            menu.forEach(option -> System.out.println(option));
            System.out.print("\nEnter the selected func(): ");
            int option = sc.nextInt();
            switch (option) {
                case 0:
                    ConnectComponent.closeConnection();
                    isRunning = false;
                    break;
                case 1:
                    comp.createTables(Globals.tablesToWorkWith3);
                    break;
                case 2:
                    comp.deleteTables(Globals.tablesToWorkWith3);
                    break;
                case 3:
                    RandomGenerator randData = new RandomGenerator();
                    randData.generateMeSome();

                    break;
                case 4:
                    comp.truncateTable(tablesToWorkWith);
                    break;
                case 5:

                    break;
                case 6:

                    break;
            }
        }
    }
}
