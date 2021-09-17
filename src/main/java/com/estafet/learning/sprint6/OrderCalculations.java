package com.estafet.learning.sprint6;

import java.util.Random;

interface OrderCalculations {
    Random rand = new Random();
    // calculate all the invoice article prices
    void generateRandomOrderData();
    void calculateOrderAmountWithVAT();
    void cutThemSomeSlackOrder(double additionalDiscountPercent) throws DiscountNotApplicableException, ShippingNotSupported;

    default double additionalDiscount() {
        double randAdditionalDiscountPercent = (rand.nextInt(4- 1) + 1)*10;
        randAdditionalDiscountPercent = 10;
        //System.out.println("\nAdditional discount percent from interface InvoiceCalculations is: " + randAdditionalDiscountPercent);
        return randAdditionalDiscountPercent;
    }
    void printObjectProperties();
}
