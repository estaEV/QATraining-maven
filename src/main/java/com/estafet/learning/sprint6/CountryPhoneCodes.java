package com.estafet.learning.sprint6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountryPhoneCodes {
    private Map<String, String> phoneCodesMap = new HashMap<>();

    /**
     * Checks if the result of the passed regex pattern is present into the passed string.
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
     * Opens a json containing each country phone prefix. Using regex it filters the countries and the phone codes and puts them into a HashMap where the key are the countries and the values are phones codes.
     */
    public void generateHashMap() {
        String currentLine = null;
        String regexResult = null;
        String regexResult2 = null;
        String regexResultDigit = null;
        List<String> countries = new ArrayList();;
        List<String> phoneCodes =  new ArrayList();;

        try {
            File myObj = new File("C:\\TrainingPlanProjects\\QATraining\\country-codes.json");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                currentLine = myReader.nextLine();

                if (currentLine.contains("name")) {
                    regexResult2 = currentLine.substring(14);
                    regexResult = regexChecker("([a-zA-Z]+\\s?)+", regexResult2);
                    countries.add(regexResult);
                }

                else if (currentLine.contains("code")) {
                    regexResultDigit = regexChecker("\\+?([0-9]\\s?)+", currentLine);
                    phoneCodes.add(regexResultDigit);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while trying to open the file.");
            e.printStackTrace();
        }

        for (int i = 0; i < countries.size(); i++) {
                phoneCodesMap.put(countries.get(i), phoneCodes.get(i));
        }
        for (String j : phoneCodesMap.keySet()) {
            System.out.println("key: " + j + " value: " + phoneCodesMap.get(j));
        }
    }
}
