package org.example.mealprepmain.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MealDatabaseHelper {

    public static void saveMeal(int userId, String mealName, String ingredients) {
        String sql = "INSERT INTO meals(user_id, meal_name, ingredients) VALUES (?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, mealName);
            pstmt.setString(3, ingredients);
            pstmt.executeUpdate();
            System.out.println("Meal saved to database.");
        } catch (SQLException e) {
            System.out.println("Error saving meal: " + e.getMessage());
        }
    }

    public static List<String> getMealsForUser(int userId) {
        List<String> meals = new ArrayList<>();
        String sql = "SELECT meal_name FROM meals WHERE user_id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                meals.add(rs.getString("meal_name"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving meals: " + e.getMessage());
        }

        return meals;
    }
}
