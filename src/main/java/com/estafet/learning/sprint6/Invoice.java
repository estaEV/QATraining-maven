package com.estafet.learning.sprint6;

import java.lang.reflect.Field;
import java.util.Map;

abstract public class Invoice implements TradeInvoiceCalculations {

    private int invoiceNumber;
    private String clientDetails;
    private String VATNumber;
    // instead of put method in a static block
    // = Map.of("NZXT H510", 74.99, "AMD Ryzen 5 5700G", 369.99, "Asus ROG Strix B550-E Gaming", 209.07,
    // "Nvidia GeForce RTX 3060 Ti", 2899.99, "Corsair Vengeance LPX 16GB (2x8GB) DDR4-3200", 77.99, "Addlink S70 512GB NVMe SSD", 84.99, "WD Black 1TB", 69.99, "Corsair TX650M 650W", 129.99);// = new HashMap<String, String>();
    private Map<String, Double> articles;
    private double totalAmountBeforeVAT;
    private double totalAmountBeforeVATWithDiscount;
    private double discountedAmount;
    private double totalAmountAfterVAT;

    private static final int MAX_AMOUNT_PER_SINGLE_INVOICE;
    private static final String INVOICE_NAME;

    static {
        // make here the static list of articles
        MAX_AMOUNT_PER_SINGLE_INVOICE = 10000;
        INVOICE_NAME = "DefaultInvoiceName";
        //articlesToPass = new ArrayList<>(List.of("NZXT H510", "AMD Ryzen 5 5700G", "Asus ROG Strix B550-E Gaming", "Nvidia GeForce RTX 3060 Ti", "Corsair Vengeance LPX 16GB (2x8GB) DDR4-3200", "Addlink S70 512GB NVMe SSD", "WD Black 1TB", "Corsair TX650M 650W"));
    }

    Invoice() {
        //System.out.println("I am an Invoice empty constructor.");
    }


    public Invoice(int invoiceNumber, String clientDetails, Map<String, Double> articles, double totalAmountBeforeVAT, double totalAmountBeforeVATWithDiscount, double discountedAmount, double totalAmountAfterVAT) {
        this.invoiceNumber = invoiceNumber;
        this.clientDetails = clientDetails;
        this.articles = articles;
        this.totalAmountBeforeVAT = totalAmountBeforeVAT;
        this.totalAmountBeforeVATWithDiscount = totalAmountBeforeVATWithDiscount;
        this.discountedAmount = discountedAmount;
        this.totalAmountAfterVAT = totalAmountAfterVAT;
        System.out.println("I am an Invoice full constructor.");
    }

    public String getVATNumber() {
        return VATNumber;
    }

    public void setVATNumber(String VATNumber) {
        this.VATNumber = VATNumber;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(String clientDetails) {
        this.clientDetails = clientDetails;
    }

    public Map<String, Double> getArticles() {
        return articles;
    }

    public void setArticles(Map<String, Double> articles) {
        this.articles = articles;
    }

    public double getDiscountedAmount() {
        return discountedAmount;
    }

    public void setDiscountedAmount(double discountedAmount) {
        this.discountedAmount = discountedAmount;
    }

    public double getTotalAmountBeforeVAT() {
        return totalAmountBeforeVAT;
    }

    public void setTotalAmountBeforeVAT(double totalAmountBeforeVAT) {
        this.totalAmountBeforeVAT = totalAmountBeforeVAT;
    }

    public double getTotalAmountBeforeVATWithDiscount() {
        return totalAmountBeforeVATWithDiscount;
    }

    public void setTotalAmountBeforeVATWithDiscount(double totalAmountBeforeVATWithDiscount) {
        this.totalAmountBeforeVATWithDiscount = totalAmountBeforeVATWithDiscount;
    }

    public double getTotalAmountAfterVAT() {
        return totalAmountAfterVAT;
    }

    public void setTotalAmountAfterVAT(double totalAmountAfterVAT) {
        this.totalAmountAfterVAT = totalAmountAfterVAT;
    }

    public static int getMaxAmountPerSingleInvoice() {
        return MAX_AMOUNT_PER_SINGLE_INVOICE;
    }

    public static String getInvoiceName() {
        return INVOICE_NAME;
    }


/*    public static void printObjectPropertiesStatic(List<Invoice> invoiceObjects) {
        for (Invoice item : invoiceObjects) {
            System.out.println("Item: " + item.toString());
        }
    }*/


    @Override
    public boolean equals(Object obj)
    {
        // checking if both the object references are
        // referring to the same object.
        if (this == obj) {
            System.out.println("Object and passed arg references are referring to the same object");
            return true;
        }
        // it checks if the argument is of the
        // type Geek by comparing the classes
        // of the passed argument and this object.
        // if(!(obj instanceof Geek)) return false; ---> avoid.
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        // type casting of the argument.
        Invoice ordersObj = (Invoice) obj;

        // comparing the state of argument with
        // the state of 'this' Object.
        boolean areAllFieldsEqual = ordersObj.invoiceNumber == this.invoiceNumber && ordersObj.clientDetails == this.clientDetails && ordersObj.articles == this.articles && ordersObj.totalAmountAfterVAT == this.totalAmountAfterVAT;
        System.out.printf("\nOverwritten Invoice's equals() here: %s\n", areAllFieldsEqual);
        return areAllFieldsEqual;
    }


    @Override
    public int hashCode()
    {
        int  newHashCodeNum = (this.invoiceNumber * this.clientDetails.hashCode() * (int) this.totalAmountAfterVAT);
        System.out.printf("Overwritten Invoice's equals() here: %s\n", newHashCodeNum);
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
        Field[] fields = this.getClass().getSuperclass().getDeclaredFields();

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
                            result.append(String.format("%.2f", field.get(this)));
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
