package com.estafet.learning.sprint6;

public class OrderNotFoundException extends Exception{
    public OrderNotFoundException(String msg) {
        super(msg);
        System.out.println("Currently we are inside OrderNotFoundException().");
    }
}
