package com.estafet.learning.sprint6;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

public class OrderSession {
    private Random rand = new Random();

    private int year; // = rand.nextInt(2021 + 1 - 2010) + 2010;
    private double amount; // = 1 + (1000 - 1) * rand.nextDouble();
    private boolean isEOrder;
    private int maxNumOfPages;
    private double numberOfPages;
    private double numberOfPagesLeft;
    public String employSignature;

    OrderSession (int year, double amount, boolean isEOrder, int maxNumOfPages, double numberOfPages, double numberOfPagesLeft, String employSignature) {
        this.year = year;
        this.amount = amount;
        this.isEOrder = isEOrder;
        this.maxNumOfPages = maxNumOfPages;
        this.numberOfPages = numberOfPages;
        this.numberOfPagesLeft = numberOfPagesLeft;
        this.employSignature = employSignature;
        System.out.println("I am an OrderSession (int year, double amount, boolean isEOrder, int maxNumOfPages, double numberOfPages, double numberOfPagesLeft, String employSignature) constructor.");
    }

    static void someTest(Order obj) {

    }

    static public void printObjectProperties(List<OrderSession> orderSessionsFields) {
        for (OrderSession item : orderSessionsFields) {
            System.out.println("Item: " + item.toString());
        }
    }

    public void changeEmploySignature(String employSignature) {
        //List <String> employNames = new ArrayList<String>();
        String employNames[] = { "Ace", "Arlo ", "Axel ", "Beckett ", "Bishop ", "Bowie ", "Brooks ", "Bryant ", "Camden ", "Chance ", "Colt ", "Crew ", "Dallas ", "Duke ", "Easton ", "Eddison ", "Ellis ", "Ender ", "Felix ", "Flynn ", "Fox ", "Greyson ", "Griffin ", "Holden ", "Hugo ", "Jace ", "Jagger ", "Jax ", "Jett ", "Jones ", "Kai ", "Knox ", "Leo ", "Lincoln ", "Max ", "Maverick ", "Miller ", "Orion ", "Oscar ", "Otis ", "Peyton ", "Phoenix ", "Pierce ", "Porter ", "Roman ", "Rowan ", "Ryder ", "Smith ", "Thompson ", "Wilder ", "Wyatt ", "Xavier ", "Zane ", "Ada ", "Aiden ", "Alma ", "Anais ", "Arden ", "Arya ", "Audrey ", "Baker ", "Bay ", "Bea ", "Beatrix ", "Birdie ", "Blaire ", "Briar ", "Brooklyn ", "Callie ", "Charlie ", "Clementine ", "Coco ", "Cody ", "Cora ", "Della ", "Dixie ", "Ellie ", "Eloise ", "Embry ", "Esme ", "Everly ", "Evie ", "Flora ", "Frankie ", "Hallie ", "Harley ", "Harlow ", "Harper ", "Hazel ", "Ida ", "Isla ", "Jade ", "Jolie ", "Joss ", "June ", "Kaia ", "Kira ", "Lace ", "Layla ", "Lennon ", "Lola ", "Lucy ", "Luna ", "Mabel ", "Mae ", "Magnolia ", "Maisie ", "Mila ", "Nella ", "Opal ", "Parker ", "Phoebe ", "Piper ", "Polly ", "Quinn ", "Rain ", "Ramona ", "Rebel ", "River ", "Rogue ", "Rosie ", "Ruby ", "Sadie ", "Sage ", "Sawyer ", "Shea ", "Sia ", "Stella ", "Storm ", "Tallulah ", "Vera ", "Willa ", "Willow ", "Wren ", "Xena"};
        int int_random = 0;
        do {
            int_random = rand.nextInt(employNames.length);
            this.employSignature = employNames[int_random];
        } while (!(this.employSignature.equals(employNames[int_random])));
    }

    //method void usedPages(double pages) - which decreases the value of numberOFPagesLeft with the amount of the entered value of the parameter ;
    //-if the value of the argument is higher than maxNumOfPages , print a warning message ;

    public void usedPages(double pagesToSubtract) {
        if (pagesToSubtract > maxNumOfPages) {
            System.out.println("Passed argument is greater than the maximum number of pages. We are unable to proceed.");
        } else {
            numberOfPagesLeft -= pagesToSubtract;
        }
    }



    // Default toString func from the net, so we dont need to import more bs
    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }
}
