package com.estafet.learning.sprint6;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com.estafet.learning.sprint6.Globals.ARTICLES_DEFAULT_MAP;
import static java.lang.Math.round;

public class Order implements com.estafet.learning.sprint6.OrderCalculations {

    public Map<String, Double> itemsToPass = new LinkedHashMap<String, Double>();
    private int orderNumber;
    private String clientDetails;
    private Map<String, Double> items;
    private String[] originCountry;
    private String[] destinationCountry;
    private double orderTotalAmountBeforeVAT;
    private double orderDiscountedAmount;
    private double orderTotalAmountBeforeVATWithDiscount;
    private double orderTotalAmountAfterVAT;
    private int orderInvoiceNumber;
    private List<Order> orderObjectsListLocal = new ArrayList<>();


    private static final int MAX_NUMBER_OF_ORDERS_PER_CLIENT;

    static {
        MAX_NUMBER_OF_ORDERS_PER_CLIENT = 10000;
    }

    public Order() {
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(String clientDetails) {
        this.clientDetails = clientDetails;
    }

    public Map<String, Double> getItems() {
        return items;
    }

    public void setItems(Map<String, Double> items) {
        this.items = items;
    }

    public String[] getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String[] originCountry) {
        this.originCountry = originCountry;
    }

    public String[] getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String[] destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public double getOrderTotalAmountBeforeVATWithDiscount() {
        return orderTotalAmountBeforeVATWithDiscount;
    }

    public void setOrderTotalAmountBeforeVATWithDiscount(double orderTotalAmountBeforeVATWithDiscount) {
        this.orderTotalAmountBeforeVATWithDiscount = orderTotalAmountBeforeVATWithDiscount;
    }

    public double getOrderDiscountedAmount() {
        return orderDiscountedAmount;
    }

    public void setOrderDiscountedAmount(double orderDiscountedAmount) {
        this.orderDiscountedAmount = orderDiscountedAmount;
    }

    public double getOrderTotalAmountBeforeVAT() {
        return orderTotalAmountBeforeVAT;
    }

    public void setOrderTotalAmountBeforeVAT(double orderTotalAmountBeforeVAT) {
        this.orderTotalAmountBeforeVAT = orderTotalAmountBeforeVAT;
    }

    public double getOrderTotalAmountAfterVAT() {
        return orderTotalAmountAfterVAT;
    }

    public void setOrderTotalAmountAfterVAT(double orderTotalAmountAfterVAT) {
        this.orderTotalAmountAfterVAT = orderTotalAmountAfterVAT;
    }

    public int getOrderInvoiceNumber() {
        return orderInvoiceNumber;
    }

    public void setOrderInvoiceNumber(int orderInvoiceNumber) {
        this.orderInvoiceNumber = orderInvoiceNumber;
    }


    public void addCurrentElementToTheList() {
        orderObjectsListLocal.add(this);
    }

    public void generateRandomOrderData()
    {
        double passTotalAmountBeforeVAT = 0;

        int orderNumber = rand.nextInt(27000 - 19000) + 19000;
        setOrderNumber(orderNumber);
        setClientDetails(UUID.randomUUID().toString());

        // not preciese, because if we have overlapping in the rand number generator we are gonna have 1 item less in the basket.
        int numberOfItemsIntoTheBasket = rand.nextInt(8 - 3) + 3;

        ArrayList<Integer> listOfItems = new ArrayList<>();
        for (int i = 0; i <= numberOfItemsIntoTheBasket; i++)
        {
            int randItemFromTheList = rand.nextInt(8 - 1) + 1;
            if (!(listOfItems.contains(randItemFromTheList)))
            {
                listOfItems.add(randItemFromTheList);
                Object currentItem = ARTICLES_DEFAULT_MAP.keySet().toArray()[randItemFromTheList];
                double currentItemPrice = ARTICLES_DEFAULT_MAP.get((ARTICLES_DEFAULT_MAP.keySet().toArray())[randItemFromTheList]);
                itemsToPass.put((String) currentItem, currentItemPrice);
                //System.out.println(currentItem + " " + currentItemPrice);
                passTotalAmountBeforeVAT += currentItemPrice;
            }
            else
            {
                --numberOfItemsIntoTheBasket;
            }
        }

        setOrderTotalAmountBeforeVAT(passTotalAmountBeforeVAT);
        setItems(itemsToPass);

/*        System.out.println("totalAmountToPass: " + passTotalAmountBeforeVAT);
        for (String i : articlesToPass.keySet()) {
            System.out.println("key: " + i + " value: " + articlesToPass.get(i));
        }*/
    }

    public void cutThemSomeSlackOrder(double additionalDiscountPercent) throws DiscountNotApplicableException, ShippingNotSupported {
        //System.out.printf("Additional discount percent is: %s percents\n\n", additionalDiscountPercent);

        double passDiscountedAmount = 0;
        double passTotalAmountBeforeVATWithDiscount = getOrderTotalAmountBeforeVAT();

/*        if (passTotalAmountBeforeVATWithDiscount < 500 ) {
            throw new DiscountNotApplicableException("Total amount not reached for the particular discount. Minimum amount is 1000. Current amount: " + passTotalAmountBeforeVATWithDiscount);
        }

        if (passTotalAmountBeforeVATWithDiscount < 1500 ) {
            throw new ShippingNotSupported("Shipping is not supported for the following amount: " + passTotalAmountBeforeVATWithDiscount);
        }*/

        double randAdditionalDiscountPercent = additionalDiscountPercent;
        int randDiscountPercent = (rand.nextInt(5 - 1) + 1)*10;

        randDiscountPercent += randAdditionalDiscountPercent;

        passDiscountedAmount = (passTotalAmountBeforeVATWithDiscount*randDiscountPercent)/100;

        passTotalAmountBeforeVATWithDiscount = (passTotalAmountBeforeVATWithDiscount - ((passTotalAmountBeforeVATWithDiscount*randDiscountPercent)/100));
        setOrderDiscountedAmount(passDiscountedAmount);
        setOrderTotalAmountBeforeVATWithDiscount(passTotalAmountBeforeVATWithDiscount);
    }


    public void calculateOrderAmountWithVAT() {
        double passTotalAmountAfterVAT = getOrderTotalAmountBeforeVATWithDiscount();
        passTotalAmountAfterVAT += (passTotalAmountAfterVAT*20)/100;
        setOrderTotalAmountAfterVAT(passTotalAmountAfterVAT);
    }

    public void saveOrderObjectToFile() throws IOException {
        // BufferedWriter bw = null;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\TrainingPlanProjects\\QATraining\\OrderPropertiesOutput.txt"))) {

            bw.write("\n" + String.valueOf(this.itemsToPass) + ", ");
            bw.write("\n" + this.getOrderNumber() + ", ");
            bw.write("\n" + this.getClientDetails() + ", ");
            bw.write("\n" + this.getOrderTotalAmountBeforeVAT() + ", ");
            bw.write("\n" + this.getOrderTotalAmountBeforeVATWithDiscount() + ", ");
            bw.write("\n" + this.getOrderDiscountedAmount() + ", ");
            bw.write("\n" + this.getOrderDiscountedAmount() + ", ");
            bw.write("\n" + this.getOrderTotalAmountAfterVAT() + ", ");
        } catch(Exception e) {
            System.out.println("Directory or file not found.");
            e.printStackTrace();
        }
        finally {
           // System.out.println("Finally still exist in try with resources block.");
/*            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                e.printStackTrace();*/
        }
    }

    public static void printObjectPropertiesStatic(List<Order> ordersObjects) {
        for (Order item : ordersObjects) {
            System.out.println("Item: " + item.toString());
        }
    }

    public void printObjectProperties() {
            System.out.println("Item: " + this.toString());
    }


    // Entry point
    public void ExecuteActionsOnOrder ()
    {
        generateRandomOrderData();
        try {
            cutThemSomeSlackOrder(additionalDiscount());
        } catch (DiscountNotApplicableException | ShippingNotSupported e) {
            e.printStackTrace();
        }
        calculateOrderAmountWithVAT();
        //printObjectProperties();
        try {
            saveOrderObjectToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean equals(Object obj)
    {
        // checking if both the object references are
        // referring to the same object.
        if (this == obj) {
            System.out.println("Object and passed arg references are referring to the same object");
            return true;
        }
        // if(!(obj instanceof Geek)) return false; ---> avoid.
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        // type casting of the argument.
        Order ordersObj = (Order) obj;

        // comparing the state of argument with
        // the state of 'this' Object.
        boolean areAllFieldsEqual = ordersObj.orderNumber == this.orderNumber && ordersObj.clientDetails == this.clientDetails && ordersObj.items == this.items && ordersObj.orderTotalAmountAfterVAT == this.orderTotalAmountAfterVAT;
        return areAllFieldsEqual;
    }


    @Override
    public int hashCode()
    {
        int  newHashCodeNum = (this.orderNumber * this.clientDetails.hashCode() * (int) this.orderTotalAmountAfterVAT);
        System.out.printf("Overwritten Order's equals() here: %s\n", newHashCodeNum);
        return newHashCodeNum;
    }


    @Override
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
            try {
                if (field.get(this) != null ) {

                    if (field.get(this) instanceof Double) {

                        if ((Double)field.get(this) != 0) {
                            result.append("  ");
                            result.append(field.getName());
                            result.append(": ");
                            //requires access to private field:
                            double roundedToTwoPlaces = new BigDecimal((Double) field.get(this))
                                    .setScale(2, RoundingMode.HALF_UP).doubleValue();
                            result.append(roundedToTwoPlaces);
                            result.append(newLine);
                        }
                        else {continue;}
                    }
                    else {
                        result.append("  ");
                        result.append(field.getName());
                        result.append(": ");
                        //requires access to private field:
                        result.append(field.get(this));
                        result.append(newLine);
                    }
                }

            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
        }
        result.append("}\n");

        return result.toString();
    }
}


