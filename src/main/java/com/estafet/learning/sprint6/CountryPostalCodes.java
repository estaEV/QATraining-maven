package com.estafet.learning.sprint6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountryPostalCodes {

    public Map<String, String> phonePostalCodesMap = new LinkedHashMap<>();

    /**
     * Check if the result of the passed regex pattern is present into the passed string.
     * @param theRegex
     * @param str2Check
     * @return
     */
    public String regexChecker(String theRegex, String str2Check) {
        Pattern checkRegex = Pattern.compile(theRegex);
        Matcher regexMatcher = checkRegex.matcher(str2Check);
        String result = null;

        while(regexMatcher.find()){
            if(regexMatcher.group().length() != 0) {
                result = regexMatcher.group().trim();
                return result;
            }
        }
        return result;
    }

    /**
     * Issue - One postal can be related to many places - revert key/value.
     * Issue - We can have same places in different states. This causes the hashmap to add the second value (zipcode) to the first one and create a fictional zip code.
     * Issue - Add german alphabet letters into the filtering of the place.
     * Issue - Regex wasnt matching some of the places.
     *
     * Opens a json containing each zip codes of cities in Germany. Using regex it filters the cities and the zip codes and puts them into a HashMap where the key are the cities and the values are zip codes.
     */
    public void generateHashMap() {
        String currentLine = null;
        String regexResult = null;
        String regexResultDigit = null;
        List<String> countries = new ArrayList();;
        List<String> postalCodes =  new ArrayList();;

        try {
            File myObj = new File("C:\\TrainingPlanProjects\\QATraining\\zipcodes-de.json");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                currentLine = myReader.nextLine();
                //regexResult = regexChecker("\"place\": \\s?\"([a-zA-ZäöüÄÖÜß\\s/-])+\"", currentLine);
                regexResult = regexChecker("\"place\": \\s?([\"'])(?:(?=(\\\\?))\\2.)*?\\1", currentLine);
                regexResultDigit = regexChecker("\"zipcode\":\\s\"[0-9]+\"", currentLine);

                if(regexResultDigit != null) {
                    regexResultDigit = regexChecker("[0-9]+", regexResultDigit);
                    postalCodes.add(regexResultDigit);
                }

                else if(regexResult != null) {
                    regexResult = regexResult.substring(10);
                    regexResult = regexResult.substring(0, regexResult.length() - 1);
                    countries.add(regexResult);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while trying to open the file.");
            e.printStackTrace();
        }

        for (int i = 0; i < countries.size(); i++) {
            if (phonePostalCodesMap.containsKey(countries.get(i))) {
                StringBuilder sb = new StringBuilder();
                String oldValue = phonePostalCodesMap.get(countries.get(i));
                String newValue = postalCodes.get(i);
                sb.append(oldValue);
                sb.append(" | ");
                sb.append(newValue);
                phonePostalCodesMap.put(countries.get(i), sb.toString());
                //phonePostalCodesMap.merge(countries.get(i), postalCodes.get(i), (x, y) -> (x + " | " + y));
            }
            else {
                phonePostalCodesMap.put(countries.get(i), postalCodes.get(i));
            }
        }
        for (String j : phonePostalCodesMap.keySet()) {
            System.out.println("place: " + j + " zipCode: " + phonePostalCodesMap.get(j));
        }
    }
}
