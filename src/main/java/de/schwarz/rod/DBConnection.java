package de.schwarz.rod;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private static String dbName = "lesenindeutschland";
    private static String username = "root";
    private static String password = "";
    private static String url = "jdbc:mysql://localhost:3306/" + dbName + "?user=" + username
                                                                + "&password=" + password ;
    private static String driverName = "com.mysql.jdbc.Driver";
    private static Connection connection;


    public static Connection getConnection(String databaseName, String port) {

        if(databaseName != null && port != null) {
            url = "jdbc:mysql://localhost:" + port + "/" + databaseName + "?user=" + username
                    + "&password=" + password;
        }

        try {
            Class.forName(driverName).newInstance();
            try {
                connection = DriverManager.getConnection(url);
            }
            catch (SQLException e) {
                System.out.println("DB connection error: " + e.toString());
            }
        }
        catch (Exception e) {
            System.out.println("DB driver not found: " + e.getMessage());
            System.out.println("DB CLASS NOT FOUND: " + e.getClass());
        }

        return connection;
    }

}
