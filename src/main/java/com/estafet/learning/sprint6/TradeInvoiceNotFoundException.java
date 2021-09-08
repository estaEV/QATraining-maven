package com.estafet.learning.sprint6;

public class TradeInvoiceNotFoundException extends Exception{
    public TradeInvoiceNotFoundException (String msg) {
        super(msg);
        System.out.println("Currently we are inside TradeInvoiceNotFoundException().");
    }
}
