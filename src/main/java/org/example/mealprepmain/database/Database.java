package org.example.mealprepmain.database;

import org.example.mealprepmain.Meal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static void initializeDatabase() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE NOT NULL," +
                "email TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL," +
                "first_name TEXT," +
                "last_name TEXT," +
                "birthday TEXT," +
                "preferences TEXT" +
                ");";

        String createMealsTable = "CREATE TABLE IF NOT EXISTS meals (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "meal_name TEXT NOT NULL," +
                "ingredients TEXT," +
                "FOREIGN KEY (user_id) REFERENCES users(id)" +
                ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createMealsTable);
            System.out.println("Users and Meals tables created or already exist");
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    public static List<Meal> loadMeals(int userId) {
        List<Meal> meals = new ArrayList<>();
        String sql = "SELECT meal_name, ingredients FROM meals WHERE user_id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String mealName = rs.getString("meal_name");
                String ingredientsString = rs.getString("ingredients");
                List<String> ingredients = List.of(ingredientsString.split(","));
                meals.add(new Meal(mealName, ingredients));
            }
        } catch (SQLException e) {
            System.out.println("Error loading meals: " + e.getMessage());
        }
        return meals;
    }


    public static void saveMeal(int userId, String mealName, String ingredients) {
        String sql = "INSERT INTO meals(user_id, meal_name, ingredients) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, mealName);
            pstmt.setString(3, ingredients);

            pstmt.executeUpdate();
            System.out.println("Saved meal to database: " + mealName);
        } catch (SQLException e) {
            System.out.println("Error saving meal: " + e.getMessage());
        }
    }
}
