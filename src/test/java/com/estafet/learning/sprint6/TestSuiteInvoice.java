package com.estafet.learning.sprint6;

import org.junit.platform.suite.api.*;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

/**
 * The JUnitPlatformSuiteDemo will discover and run all tests in the example package and its subpackages.
 * By default, it will only include test classes whose names either begin with Test or end with Test or Tests.
 */

@RunWith(org.junit.platform.runner.JUnitPlatform.class)
@SuiteDisplayName("JUnit Platform Suite Demo")
//@SelectPackages("com.estafet.learning.sprint6")
@SelectClasses({TradeInvoiceTest2.class})
public class TestSuiteInvoice {

}