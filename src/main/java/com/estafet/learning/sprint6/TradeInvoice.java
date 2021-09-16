package com.estafet.learning.sprint6;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.UnaryOperator;

import static com.estafet.learning.sprint6.Globals.*;

public class TradeInvoice extends Invoice {
    public Random rand = new Random();
    public Map<String, Double> articlesToPass = new LinkedHashMap<String, Double>();

    /**
     * Generates random data for populating an invoice object. It calls the getters and setters of the class.
     */
    public void generateRandomTradeInvoiceData()
    {
        double passTotalAmountBeforeVAT = 0;

        //Supplier<Integer> dice = () -> ThreadLocalRandom.current().nextInt(190000, 27000);
        int invoiceNumber = rand.nextInt(27000 - 19000) + 19000;
        setInvoiceNumber(invoiceNumber);
        String clientDetailsToPass = UUID.randomUUID().toString();
        setClientDetails(clientDetailsToPass);

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
                articlesToPass.put((String) currentItem, currentItemPrice);
                //System.out.println(currentItem + " " + currentItemPrice);
                passTotalAmountBeforeVAT += currentItemPrice;
            }
            else
            {
                --numberOfItemsIntoTheBasket;
            }
        }

        setTotalAmountBeforeVAT(passTotalAmountBeforeVAT);
        setArticles(articlesToPass);

        /*  System.out.println("totalAmountToPass: " + passTotalAmountBeforeVAT);
        for (String i : articlesToPass.keySet()) {
            System.out.println("key: " + i + " value: " + articlesToPass.get(i));
        }*/
    }


    /**
     * Adds a discount to the setTotalAmountBeforeVAT and sets the rest of the values of the object. It also adds the additional discount param.
     * VAT is calculated on the discounted price of the product. Is it better the retrieve the prices (with get function) inside of the method or to pass them as arguments?
     * @param additionalDiscountPercent
     * @throws DiscountNotApplicableException
     * @throws ShippingNotSupported
     */
    public void cutThemSomeSlack(double additionalDiscountPercent) throws DiscountNotApplicableException, ShippingNotSupported {
        //System.out.printf("Additional discount percent is: %s percents\n\n", additionalDiscountPercent);

        double passDiscountedAmount = 0;
        double passTotalAmountBeforeVATWithDiscount = getTotalAmountBeforeVAT();

        if (passTotalAmountBeforeVATWithDiscount < 100 ) {
            throw new DiscountNotApplicableException("Total amount not reached for the particular discount. Minimum amount is 1000. Current amount: " + passTotalAmountBeforeVATWithDiscount);
        }

        if (passTotalAmountBeforeVATWithDiscount < 120 ) {
            throw new ShippingNotSupported("Shipping is not supported for the following amount: " + passTotalAmountBeforeVATWithDiscount);
        }

        double randAdditionalDiscountPercent = additionalDiscountPercent;
        int randDiscountPercent = (rand.nextInt(5 - 1) + 1)*10;

        randDiscountPercent += randAdditionalDiscountPercent;

        passDiscountedAmount = (passTotalAmountBeforeVATWithDiscount*randDiscountPercent)/100;

        passTotalAmountBeforeVATWithDiscount = (passTotalAmountBeforeVATWithDiscount - ((passTotalAmountBeforeVATWithDiscount*randDiscountPercent)/100));
        setDiscountedAmount(passDiscountedAmount);
        setTotalAmountBeforeVATWithDiscount(passTotalAmountBeforeVATWithDiscount);
    }

    /**
     * Generates an additional discount used in cutThemSomeSlack. Method is used to represent interface inheritance and default functions.
     * @return
     */
    public double additionalDiscount() {
        int randAdditionalDiscountPercent = (rand.nextInt(4 - 1) + 1) * 10;
        //randAdditionalDiscountPercent = 50;
        //System.out.println("\nAdditional discount percent from interface TradeInvoiceCalculations is: " + randAdditionalDiscountPercent + "\n");
        return randAdditionalDiscountPercent;
    }


    /**
     * Gets getTotalAmountBeforeVATWithDiscount and add to it a 20% of VAT taxes.
     */
/*    public void calculateInvoiceWithVAT() {
        double passTotalAmountAfterVAT = getTotalAmountBeforeVATWithDiscount();
        passTotalAmountAfterVAT += (passTotalAmountAfterVAT*20)/100;
        setTotalAmountAfterVAT(passTotalAmountAfterVAT);
    }*/


    /**
     * Same as above method - applies VAT tax of 1.2. UnaryOperator - takes and returns the same arg type.
     */
    UnaryOperator<Double> addVAT = x -> x*1.2;


/*    public static void  printObjectPropertiesList(List<Invoice> ordersObjects) {
        for (Invoice item : ordersObjects) {
            System.out.println("Item: " + item.toString());
        }
    }*/

    public void printObjectProperties() {
        System.out.print(String.format("Item: %s", this));
    }


    /**
     * Saves the lastly generated object into a file.
     * @throws IOException
     */
    public void saveInvoiceObjectToFile() throws IOException {
        // BufferedWriter bw = null;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\TrainingPlanProjects\\QATraining\\InvoicePropertiesOutput.txt"))) {
                    bw.write("\n" + String.valueOf(this.articlesToPass) + ", ");
                    bw.write("\n" + this.getInvoiceNumber() + ", ");
                    bw.write("\n" + this.getClientDetails() + ", ");
                    bw.write("\n" + this.getTotalAmountBeforeVAT() + ", ");
                    bw.write("\n" + this.getTotalAmountBeforeVATWithDiscount() + ", ");
                    bw.write("\n" + this.getDiscountedAmount() + ", ");
                    bw.write("\n" + this.getDiscountedAmount() + ", ");
                    bw.write("\n" + this.getTotalAmountAfterVAT() + ", ");
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

    /**
     * Entry point used by the object in main(). It makes calls to the other functions.
     */


    public void ExecuteActions () throws IOException {
            this.generateRandomTradeInvoiceData();
            try {
                this.cutThemSomeSlack(additionalDiscount());
            } catch (DiscountNotApplicableException | ShippingNotSupported e) {
                System.out.println("Catch state of ExecuteActions activated. Printing stack trace:");
                e.printStackTrace();
            }

            // Add VAT. Maybe move that to the discount method?
            double passTotalAmountAfterVAT = (getTotalAmountBeforeVATWithDiscount() != 0 ? addVAT.apply(getTotalAmountBeforeVATWithDiscount()) : addVAT.apply(getTotalAmountBeforeVAT()));
            setTotalAmountAfterVAT(passTotalAmountAfterVAT);

            //this.printObjectProperties();
            try {
                this.saveInvoiceObjectToFile();
            } catch (Exception e) {
                throw e;
            }
        }
}
