package com.estafet.learning.sprint7;

import java.sql.SQLException;

public class ExceptionHandler {
    public static void handleException(Exception ex) {
        if (ex instanceof SQLException) {
            SQLException sqlException = (SQLException) ex;
            System.out.println("Error Code: " + sqlException.getErrorCode());
            System.out.println("SQL State: " + sqlException.getSQLState());
        }
        System.out.println("SQLException msg: " + ex.getMessage());
        System.out.println("Stacktrace: ");
        ex.printStackTrace();
    }
}
