package com.example.onskeliste.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Database connection singleton
public class ConnectionManager {
    private static Connection connection = null;

    public static Connection getConnection(String db_url, String username, String password){
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/onskelistefilip", "root", "bøfwellington");
            } catch (SQLException e) {
                System.out.println("Could not connect to database");
                e.printStackTrace();
            }
        }
        return connection;
    }
}
