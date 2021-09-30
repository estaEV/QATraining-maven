
package com.estafet.learning.sprint7;

import com.estafet.learning.sprint7.*;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class RandomGenerator {
    private List<Customer> customersList = null;
    private List<Product> productsList = null;
    private List<OnlineOrder> onlineOrderList = null;
    private int prodStartId = 79999;
    private int custStartId = 119999;
    private int onlineOrderStartId = 159999;

    public void generateMeSome() {
        Faker faker = new Faker();
        int length = com.estafet.learning.sprint7.Globals.STUDENTNAMES.length;

        Supplier<Integer> numOfSubjects = () -> ThreadLocalRandom.current().nextInt(6, 9);
        Supplier<Integer> dice = () -> ThreadLocalRandom.current().nextInt(1, 60);
        Supplier<Integer> numberOfThings = () -> ThreadLocalRandom.current().nextInt(120, 200);
        Supplier<Integer> year = () -> ThreadLocalRandom.current().nextInt(2018, 2022);
        Supplier<Integer> grade = () -> ThreadLocalRandom.current().nextInt(2, 6);

        customersList = new ArrayList<>();
        for (int i = 0; i < numberOfThings.get(); i++) {
            Customer fok = new Customer();
            customersList.add(fok);
            customersList.get(i).setFirst_name(faker.name().firstName());
            customersList.get(i).setLast_name(faker.name().lastName());
            custStartId = ++custStartId;
            customersList.get(i).setCustomer_number(custStartId);
            customersList.get(i).setAddress_line1(faker.address().fullAddress());
            customersList.get(i).setAddress_line2(faker.address().secondaryAddress());
            customersList.get(i).setCity(faker.address().city());
            customersList.get(i).setPostcode(faker.address().zipCode());
            customersList.get(i).setPhone(String.valueOf(faker.phoneNumber()));
            //System.out.println(stdList.get(i));
        }

        productsList = new ArrayList<>();
        for (int i = 0; i < numberOfThings.get(); i++) {
            Product fuk = new Product();
            productsList.add(fuk);
            prodStartId = ++prodStartId;
            productsList.get(i).setProduct_code(String.valueOf(prodStartId));
            productsList.get(i).setPrice(Double.parseDouble(faker.commerce().price()));
            productsList.get(i).setProduct_name(faker.commerce().productName());
            productsList.get(i).setProduct_description(faker.commerce().department());
            productsList.get(i).setQuantity(faker.number().numberBetween(20, 100));
        }

        onlineOrderList = new ArrayList<>();
        for (int i = 0; i < faker.number().numberBetween(120, 120); i++) {
            OnlineOrder fak = new OnlineOrder();
            onlineOrderList.add(fak);
            onlineOrderStartId = ++onlineOrderStartId;
            onlineOrderList.get(i).setOrder_number(onlineOrderStartId);
            onlineOrderList.get(i).setCustomer_number(faker.number().numberBetween(custStartId, customersList.size()));
            onlineOrderList.get(i).setDate(String.valueOf(faker.date()));

            List<Product> listOfProducts = null;
            double totalPrice = 0;
            for (int j = 0; j < faker.number().numberBetween(2, 8); j++) {
                listOfProducts.add(productsList.get(numberOfThings.get()));
                totalPrice = listOfProducts.get(j).getPrice();
            }

            onlineOrderList.get(i).setListOfProducts(listOfProducts);
            onlineOrderList.get(i).setTotal_price(totalPrice);
        }
    }
}


