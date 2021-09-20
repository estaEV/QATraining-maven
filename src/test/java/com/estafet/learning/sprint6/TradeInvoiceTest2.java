package com.estafet.learning.sprint6;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("TradeInvoiceTest class")
public class TradeInvoiceTest2 {

    Invoice objectTradeInvoice;
    Double[] doubleObjects;

    @BeforeEach
    void init() throws IOException {
        objectTradeInvoice = new TradeInvoice();
        objectTradeInvoice.ExecuteActions();
        doubleObjects = new Double[]{objectTradeInvoice.getTotalAmountBeforeVAT(), objectTradeInvoice.getTotalAmountAfterVAT(),
                objectTradeInvoice.getTotalAmountBeforeVATWithDiscount(), objectTradeInvoice.additionalDiscount()};
    }

    @Nested
    @DisplayName("Nested class for testing the default properties of the object")
    public class TradeInvoiceDefaultProperties {

        @Nested
        @DisplayName("Testing strings default nested class")
        public class TestStrings {

            @Test
            @DisplayName("Check if clientDetails length is 36")
            void checkIfClientDetailsLengthIs36() {
                assertEquals(36, objectTradeInvoice.getClientDetails().length());
            }

            @ParameterizedTest
            @ValueSource(strings = {"", "  "})
            @DisplayName("Check clientDetails for whitespaces or emptiness")
            void checkClientDetailsForWhitespaces(String input) {
                System.out.println("current input" + input);
                assertFalse(objectTradeInvoice.getClientDetails().contains(input));
            }

            @Test
            @DisplayName("Check clientDetails for uppercase chars")
            void checkClientDetailsForUppercaseChars() {
                assertFalse(Pattern.compile("[A-Z]").matcher(objectTradeInvoice.getClientDetails()).find());
            }
        }

        @Nested
        @DisplayName("Testing digits default nested class")
        public class TestDigits {

            @Test
            @DisplayName("Check number of digits after the decimal place")
            void checkNumberOfDigitsAfterTheDecimalPlace() {
                // maybe add
                for (Double o : doubleObjects) {
                    String textDouble = Double.toString(Math.abs(o));
                    int integerPlaces = textDouble.indexOf('.');
                    int decimalPlaces = textDouble.length() - integerPlaces - 1;
                    System.out.println(textDouble);
                    //assertEquals(2, decimalPlaces);
                    assertTrue(0 <= decimalPlaces && decimalPlaces <= 14);
                }
            }

            @Test
            @DisplayName("Test invoiceNumber field")
            void testInvoiceNumberField() {
                assertAll(
                        () -> assertNotNull(objectTradeInvoice.getInvoiceNumber()),
                        () -> assertNotEquals(0, String.valueOf(objectTradeInvoice.getInvoiceNumber()).length()),
                        () -> assertEquals(5, String.valueOf(objectTradeInvoice.getInvoiceNumber()).length()),
                        () -> assertTrue(19000 <= objectTradeInvoice.getInvoiceNumber() && objectTradeInvoice.getInvoiceNumber() <= 27000)
                );
            }

            @Test
            @DisplayName("Test if each item of the article list has a price")
            void testIfEachItemOfTheArticleListHasAPrice() {
                Map<String, Double> articles = objectTradeInvoice.getArticles();
                for (Double value : articles.values()) {
                    assertAll(
                            () -> assertNotNull(value),
                            () -> assertNotEquals(0, value));
                }
            }

            //@Disabled
            @ParameterizedTest()
            @ValueSource(ints = {8, 7, 6, 5, 4, 3, 2, 1, Integer.MAX_VALUE})
            @DisplayName("Test number of articles field")
            void testNumberOfArticlesField(int number) {
                //assertAll();
                //assertNotNull(objectTradeInvoice.getArticles());
                System.out.println("is " + number + " <= than " + objectTradeInvoice.getArticles().size());
                //assertTrue(number <= objectTradeInvoice.getArticles().size());
                assertEquals(number, objectTradeInvoice.getArticles().size());
            }


            @Test
            @DisplayName("Test totalAmountBeforeVAT field")
            void testTotalAmountBeforeVatField() {
                assertAll(
                        () -> assertNotNull(objectTradeInvoice.getTotalAmountBeforeVAT()),
                        () -> assertNotEquals(0, objectTradeInvoice.getTotalAmountBeforeVAT())
                );
            }

            @Test
            @DisplayName("Test totalAmountAfterVAT field")
            void totalAmountAfterVAT() {
                assertAll(
                        () -> assertNotNull(objectTradeInvoice.getTotalAmountAfterVAT()),
                        () -> assertNotEquals(0, objectTradeInvoice.getTotalAmountAfterVAT())
                );
            }
        }

        @Nested
        @DisplayName("Nested class for testing discount applying")
        public class TradeInvoiceDiscounts {

            @Test
            @DisplayName("Test discountedAmount field")
            void testDiscountedAmountField() {
                assertAll(
                        () -> assertNotNull(objectTradeInvoice.getDiscountedAmount()),
                        () -> assertNotEquals(0, objectTradeInvoice.getDiscountedAmount())
                );
            }

            @Test
            @DisplayName("Test totalAmountBeforeVATWithDiscount field")
            void testTotalAmountBeforeVATWithDiscountField() {
                assertAll(
                        () -> assertNotNull(objectTradeInvoice.getTotalAmountBeforeVATWithDiscount()),
                        () -> assertNotEquals(0, objectTradeInvoice.getTotalAmountBeforeVATWithDiscount())
                );
            }

            @Test
            @DisplayName(" Test DiscountNotApplicableException")
            void testDiscountNotApplicableException() {
                objectTradeInvoice.setTotalAmountBeforeVAT(90);
                // asserting that the correct exception is thrown
                Throwable error = assertThrows("no DiscountNotApplicableException exception is thrown", DiscountNotApplicableException.class, () -> objectTradeInvoice.cutThemSomeSlack(objectTradeInvoice.additionalDiscount()));
                // asserting that the exception msg is the correct one
                assertEquals("Total amount not reached for the particular discount. Minimum amount is 1000. Current amount: " + objectTradeInvoice.getTotalAmountBeforeVAT(), error.getMessage());

            }

            @Test
            @DisplayName(" Test ShippingNotSupportedException")
            void testShippingNotSupportedException() {
                objectTradeInvoice.setTotalAmountBeforeVAT(110);
                Throwable error = assertThrows("no ShippingNotSupportedException exception is thrown", ShippingNotSupported.class, () -> objectTradeInvoice.cutThemSomeSlack(objectTradeInvoice.additionalDiscount()));
                assertEquals("Shipping is not supported for the following amount: " + objectTradeInvoice.getTotalAmountBeforeVAT(), error.getMessage());
            }
        }





    }
}