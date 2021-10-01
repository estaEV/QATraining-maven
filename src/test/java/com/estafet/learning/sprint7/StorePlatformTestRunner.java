package com.estafet.learning.sprint7;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-report.html"},
        features = "classpath:/features/com.estafet.learning.sprint7",
        glue = "com.estafet.learning.sprint7")

public class StorePlatformTestRunner {


}
