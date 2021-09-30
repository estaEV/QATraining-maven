package com.estafet.learning.sprint7;

//import org.springframework.util.StopWatch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.estafet.learning.sprint7.Globals.connection;
import static java.lang.String.valueOf;

public class ConnectComponent {

    public static Connection openConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/store_platform?user=admin2&password=admin2&serverTimezone=UTC");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        }
        //System.out.printf("connection is : ", connection);
        return connection;
    }


    public void createTables(String[][] tablesToCreate) throws SQLException {

        for (int i = 0; i < tablesToCreate.length; i++) {

            StringBuilder params = new StringBuilder("");
            for (int j = 1; j < tablesToCreate[i].length; j++) {
                if (j != tablesToCreate[i].length - 1) {
                    params.append("$col").append(valueOf(j)).append(", ");
                } else {
                    params.append("$col").append(valueOf(j));
                }
            }

            String strQuery =
                    "CREATE TABLE $tableName "
                            + "(" + params + "); ";
            for (int j = 1; j < tablesToCreate[i].length; j++) {
                strQuery = strQuery
                        .replace("$col" + j, tablesToCreate[i][j]);
            }
            strQuery = strQuery
                    .replace("$tableName", tablesToCreate[i][0]);

            try (PreparedStatement preparedStatement = connection.
                    prepareStatement(strQuery);) {

                preparedStatement.executeUpdate();
            }
        }
    }

    public void deleteTables(String[][] tablesToDelete) throws SQLException {
        for (int i = 0; i < tablesToDelete.length; i++) {
            // Vulnerable to sql injection but still
            String strQuery = "DROP TABLE IF EXISTS "
                    + "$tableName;";
            String query = strQuery.replace("$tableName", tablesToDelete[i][0]);

            try (PreparedStatement preparedStatement = connection.
                    prepareStatement(query);) {
                preparedStatement.executeUpdate();
            }
        }
    }


    public void truncateTable(String[] tablesToDelete) throws SQLException {

        for (int i = 0; i < tablesToDelete.length; i++) {

            String strQuery =
                    "TRUNCATE $tableName ";

            String query = strQuery
                    .replace("$tableName", tablesToDelete[i]);

            try (PreparedStatement preparedStatement = connection.
                    prepareStatement(query)) {
                preparedStatement.executeUpdate();
            }
        }
    }

/*

    public void insertStudentsData(String[] tablesToDelete, RandomGenerator randData) throws SQLException {

        List<Student> listStud = new ArrayList<>();
        listStud = randData.getStdList();

        for (int i = 0; i < listStud.size(); i++) {
            listStud.get(i);

            String strQuery =
                    "INSERT INTO $tableName "
                            + "(name, StudentId, classYear) "
                            + "VALUES (?, ?, ?)";

            String query = strQuery
                    .replace("$tableName", tablesToDelete[0]);

            try (PreparedStatement preparedStatement = connection.
                    prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, listStud.get(i).getName());
                preparedStatement.setInt(2, listStud.get(i).getStudentId());
                preparedStatement.setInt(3, listStud.get(i).getClassYear());
                preparedStatement.executeUpdate();
            }
        }
    }

    public void insertSubjectsData(String[] tablesToDelete, RandomGenerator randData) throws SQLException {

        List<Subject> listSub = new ArrayList<>();
        listSub = randData.getSubList();

        for (int i = 0; i < listSub.size(); i++) {
            listSub.get(i);

            String strQuery =
                    "INSERT INTO $tableName "
                            + "(name, subjectId, year) "
                            + "VALUES (?, ?, ?)";

            String query = strQuery
                    .replace("$tableName", tablesToDelete[1]);

            try (PreparedStatement preparedStatement = connection.
                    prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, listSub.get(i).getSubjectName());
                preparedStatement.setString(2, String.valueOf(listSub.get(i).getSubjectId()));
                preparedStatement.setInt(3, listSub.get(i).getYearStudied());
                preparedStatement.executeUpdate();
            }
        }
    }

    public void insertGradebookData(String[] tablesToDelete, RandomGenerator randData) throws SQLException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Instant starts = Instant.now();

        List<GradeBook> listGra = new ArrayList<>();
        listGra = randData.getGraList();

        for (int i = 0; i < listGra.size(); i++) {

            listGra.get(i);
            int numbersOfSubjectsStudied = listGra.get(i).getGrades().size();
            Map<String, Integer> mapOfGrades = listGra.get(i).getGrades();

            for (Map.Entry<String, Integer> mapOfGrades2 : mapOfGrades.entrySet()) {
                String strQuery =
                        "INSERT INTO $tableName "
                                + "(studentId, subjectId, grade) "
                                + "VALUES (?, ?, ?)";

                String query = strQuery
                        .replace("$tableName", tablesToDelete[2]);

                try (PreparedStatement preparedStatement = connection.
                        prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                    preparedStatement.setInt(1, listGra.get(i).getStudentId());
                    preparedStatement.setInt(2, Integer.parseInt(mapOfGrades2.getKey()));
                    preparedStatement.setInt(3, Integer.valueOf(mapOfGrades2.getValue()));
                    preparedStatement.executeUpdate();
                }
            }
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
        Instant ends = Instant.now();
        System.out.println(Duration.between(starts, ends));
    }


    public void insertNewSubject(String[] tablesToDelete, String newSubject, String newSubjectId, int subjectYear) throws SQLException {
        String strQuery =
                "INSERT INTO $tableName "
                        + "(name, subjectId, year) "
                        + "VALUES (?, ?, ?)";

        String query = strQuery
                .replace("$tableName", tablesToDelete[1]);

        try (PreparedStatement preparedStatement = connection.
                prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, newSubject);
            preparedStatement.setString(2, newSubjectId);
            preparedStatement.setInt(3, subjectYear);
            preparedStatement.executeUpdate();
        }
    }


    public void mathAvgGrade(int studentsClassYear) throws SQLException {
        //studentsClassYear = 2018;
        double avgGrade = 0;
        String query =
                "SELECT AVG(grade) FROM college_book.gradebooks WHERE subjectId LIKE \"55598\" AND studentId IN (SELECT studentId FROM college_book.students WHERE classYear = ?);\n";
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(query);) {
            preparedStatement.setInt(1, studentsClassYear);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    System.out.println("resulseti is: " + resultSet);
                    System.out.println("resulseti is: " + preparedStatement);
                    avgGrade = resultSet.getDouble("AVG(grade)");
                }
            }
        }
        System.out.println("The AVG math grade for all students is: " + avgGrade);
    }
*/


    public static void closeConnection() throws SQLException {
            connection.close();
    }

}
