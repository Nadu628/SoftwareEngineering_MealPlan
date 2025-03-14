package org.example.mealprepmain.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:sqlite:mealprep.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Successfully connected to Database");
        } catch (SQLException e) {
            System.out.println("Database Connection failed: " + e.getMessage());
        }
        return conn;
    }
}
