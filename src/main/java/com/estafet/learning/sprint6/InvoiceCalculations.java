package com.estafet.learning.sprint6;

import java.util.Random;

interface InvoiceCalculations {
    Random rand = new Random();
    // calculate all the invoice article prices
    void generateRandomTradeInvoiceData();
    // void calculateInvoiceWithVAT();
    void cutThemSomeSlack(double additionalDiscountPercent) throws DiscountNotApplicableException, ShippingNotSupported;

    double additionalDiscount();
    void printObjectProperties();
}
