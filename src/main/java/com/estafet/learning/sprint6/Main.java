package com.estafet.learning.sprint6;

import com.estafet.learning.sprint6.DiscountNotApplicableException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws DiscountNotApplicableException {

        Scanner sc = new Scanner(System.in);
        List<String> menu = new ArrayList<>();
        System.out.println("sprint5");
        menu.add("\n0. Exit.");
        menu.add("1. Generate random TradeInvoice objects and save them to a file.");
        menu.add("2. Generate random Order objects and save them to a file.");
        menu.add("3. Search Invoice by ID.");
        menu.add("4. Search Order by ID.");
        menu.add("5. Save phone codes from json to hashmap.");
        menu.add("6. Save Germany zip codes from json to hashmap.");
        menu.add("7. Print all TradeInvoices from the list using SB.");
        menu.add("8. Print all Orders from the list using SB.");

        boolean isRunning = true;

        List<Invoice> tradeInvoiceObjectsList = new ArrayList<>();
        List<Order> orderObjectsList = new ArrayList<>();
        while (isRunning) {
            menu.forEach(option -> System.out.println(option));
            System.out.print("\nEnter the selected func(): ");
            int option = sc.nextInt();
            switch (option) {
                case 0:
                    isRunning = false;
                    break;
                case 1:
                    Invoice objectTradeInvoice = new TradeInvoice();
                    Invoice objectTradeInvoice2 = new TradeInvoice();
                    TradeInvoiceCalculations objectTradeInvoiceCalculations = new TradeInvoice();

                    tradeInvoiceObjectsList.add(objectTradeInvoice);
                    tradeInvoiceObjectsList.add(objectTradeInvoice2);

                    for (int i = 0; i < tradeInvoiceObjectsList.size(); i++) {
                        try {
                            tradeInvoiceObjectsList.get(i).ExecuteActions();
                        } catch (IOException e) {
                            System.out.println("Finally the rethrown exception from ExecuteActions() TradeInvoice is caught in the main()");
                            e.printStackTrace();
                        }
                    }
                    //objectTradeInvoiceCalculations.ExecuteActions();
                    break;
                case 2:
                    Order objectOrder = new Order();
                    Order objectOrder2 = new Order();

                    orderObjectsList.add(objectOrder);
                    orderObjectsList.add(objectOrder2);

                    for (int i = 0; i < orderObjectsList.size(); i++) {
                        orderObjectsList.get(i).ExecuteActionsOnOrder();
                    }
                    break;
                case 3:
                    Invoice foundInvoiceObj = null;
                    try {
                        foundInvoiceObj = findInvoiceByID(tradeInvoiceObjectsList);
                        foundInvoiceObj.printObjectProperties();
                    } catch (TradeInvoiceNotFoundException e) {
                        System.out.println("catch block for TradeInvoiceNotFoundException fr0m the main()");
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    Order foundOrderObj = null;
                    try {
                        foundOrderObj = findOrderByID(orderObjectsList);
                        foundOrderObj.printObjectProperties();
                    } catch (OrderNotFoundException e) {
                        System.out.println("catch block for TradeInvoiceNotFoundException from the main()");
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    CountryPhoneCodes objectCountryPhoneCodes = new CountryPhoneCodes();
                    objectCountryPhoneCodes.generateHashMap();
                    break;
                case 6:
                    CountryPostalCodes objectCountryPostalCodes = new CountryPostalCodes();
                    objectCountryPostalCodes.generateHashMap();
                    break;
                case 7:
                    printAllObjects(tradeInvoiceObjectsList);
                    break;
                case 8:
                    printAllObjects(orderObjectsList);
                    break;
            }
        }
    }


    /**
     * Print all objects from a list using StringBuilder...
     * @param list
     */
    private static void printAllObjects( List<? extends Object> list ) {
        StringBuilder sb = new StringBuilder();
        for (Object o : list) {
            sb = sb.append(o.toString());
        }
        System.out.println("printAllInvoices method output is:");
        System.out.println(sb);
    }


    /**
     * Finds an invoice from the provided list using the input invoice number. If found - it prints the object properties.
     * @param tradeInvoiceObjectsList
     * @return
     */
    private static Invoice findInvoiceByID(List<Invoice> tradeInvoiceObjectsList) throws TradeInvoiceNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Invoice number: ");
        String searchedInvoiceNumber = sc.nextLine();
        for (Invoice el : tradeInvoiceObjectsList) {
            if (searchedInvoiceNumber.equals(String.valueOf(el.getInvoiceNumber()))) {
                System.out.println("Invoice Number Exists: " + searchedInvoiceNumber);
                return el;
            }
        }
        throw new TradeInvoiceNotFoundException("Invoice not found.");
    }


    /**
     * Finds an order from the provided list using the input order number. If found - it prints the object properties.
     * @param orderObjectsList
     * @return
     */
    private static Order findOrderByID(List<Order> orderObjectsList) throws OrderNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Order number: ");
        String searchedOrderNumber = sc.nextLine();
        for (int i = 0; i < orderObjectsList.size(); i++) {
            if (searchedOrderNumber.equals(String.valueOf(orderObjectsList.get(i).getOrderNumber()))) {
                System.out.println("Order Number Exists: " + searchedOrderNumber);
                return orderObjectsList.get(i);
            }
        }
        throw new OrderNotFoundException("Order not found.");
    }
}
