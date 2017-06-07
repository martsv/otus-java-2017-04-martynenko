package ru.otus.hw9.executor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());

            String url = "jdbc:postgresql://" +    // db type
                    "localhost:" +                 // host name
                    "5432/" +                      // port
                    "db_example?" +                // db name
                    "user=admin&" +                // login
                    "password=admin";              // password

            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void example() {
        try (Connection connection = getConnection()) {
            System.out.println("Connected to: " + connection.getMetaData().getURL());
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
