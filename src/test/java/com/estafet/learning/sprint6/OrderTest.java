package com.estafet.learning.sprint6;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

// JUnit 4
//import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("OrderTest class")
public class OrderTest {

    Order objectOrder;
    Double[] doubleObjects;

    @BeforeEach
    public void init() throws IOException {
        objectOrder = new Order();
        objectOrder.ExecuteActionsOnOrder();
        doubleObjects = new Double[]{objectOrder.getOrderTotalAmountBeforeVAT(), objectOrder.getOrderTotalAmountAfterVAT(),
                objectOrder.getOrderTotalAmountBeforeVATWithDiscount(), objectOrder.getOrderDiscountedAmount()};
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
                assertEquals(36, objectOrder.getClientDetails().length());
            }

            @Test
            @DisplayName("Check clientDetails for whitespaces")
            void checkClientDetailsForWhitespaces() {
                assertFalse(objectOrder.getClientDetails().contains(" "));
            }

            @Test
            @DisplayName("Check clientDetails for uppercase chars")
            void checkClientDetailsForUppercaseChars() {
                assertFalse(Pattern.compile("[A-Z]").matcher(objectOrder.getClientDetails()).find());
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
                        () -> assertNotNull(objectOrder.getOrderNumber()),
                        () -> assertNotEquals(0, String.valueOf(objectOrder.getOrderNumber()).length()),
                        () -> assertEquals(5, String.valueOf(objectOrder.getOrderNumber()).length()),
                        () -> assertTrue(19000 <= objectOrder.getOrderNumber() && objectOrder.getOrderNumber() <= 27000)
                );
            }

            @Test
            @DisplayName("Test if each item of the article list has a price")
            void testIfEachItemOfTheArticleListHasAPrice() {
                Map<String, Double> articles = objectOrder.getItems();
                for (Double value : articles.values()) {
                    assertNotNull(value);
                    assertNotEquals(0, value);
                }
            }

            @Test
            @DisplayName("Test articles field")
            void testArticlesField() {
                assertNotNull(objectOrder.getItems());
                assertTrue(3 <= objectOrder.getItems().size() && objectOrder.getItems().size() <= 8);
            }


            @Test
            @DisplayName("Test totalAmountBeforeVAT field")
            void testTotalAmountBeforeVatField() {
                assertAll(
                        () -> assertNotNull(objectOrder.getOrderTotalAmountBeforeVAT()),
                        () -> assertNotEquals(0, objectOrder.getOrderTotalAmountBeforeVAT())
                );
            }

            @Test
            @DisplayName("Test totalAmountAfterVAT field")
            void totalAmountAfterVAT() {
                assertAll(
                        () -> assertNotNull(objectOrder.getOrderTotalAmountAfterVAT()),
                        () -> assertNotEquals(0, objectOrder.getOrderTotalAmountAfterVAT())
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
                        () -> assertNotNull(objectOrder.getOrderDiscountedAmount()),
                        () -> assertNotEquals(0, objectOrder.getOrderDiscountedAmount())
                );
            }

            @Test
            @DisplayName("Test totalAmountBeforeVATWithDiscount field")
            void testTotalAmountBeforeVATWithDiscountField() {
                assertAll(
                        () -> assertNotNull(objectOrder.getOrderTotalAmountBeforeVATWithDiscount()),
                        () -> assertNotEquals(0, objectOrder.getOrderTotalAmountBeforeVATWithDiscount())
                );
            }

/*
        @Test
        @DisplayName(" Test DiscountNotApplicableException")
        void testDiscountNotApplicableException() {
            Throwable error = assertThrows
        }
*/


        }
    }
}