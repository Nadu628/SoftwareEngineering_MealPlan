package org.example.mealprepmain;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.example.mealprepmain.database.Database;

import java.util.ArrayList;
import java.util.List;

public class RegistrationScreenController {

    @FXML
    private TextField firstNameTextField, lastNameTextField, emailTextField, birthdayTextField;
    @FXML
    private PasswordField passwordField, confirmPasswordField;
    @FXML
    private CheckBox vegetarianCheckBox, veganCheckBox, glutenFreeCheckBox, dairyFreeCheckBox, ketoCheckBox, paleoCheckBox,
            lowSodiumCheckBox, lowCarbCheckBox, highProteinCheckBox, nutFreeCheckBox;
    @FXML
    Pane registrationPane;
    @FXML
    ImageView eatingImageView;

    private User user;

    @FXML
    public void initialize() {
        System.out.println("RegistrationScreenController initialized");
    }

    @FXML
    public void handleSubmit(){
        // Validate passwords match
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showAlert("Passwords do not match!", "Please make sure your passwords match.");
            return;
        }

        // Validate password is not empty
        if (passwordField.getText().isEmpty()) {
            showAlert("Password required", "Please enter a password.");
            return;
        }

        // Hash the password before storing
        String hashedPassword = HandlePasswordHash.hashPassword(passwordField.getText());

        //collect food preferences
        List<String> preferences = new ArrayList<>();
        if(vegetarianCheckBox.isSelected()){
            preferences.add("vegetarian");
        }
        if(veganCheckBox.isSelected()){
            preferences.add("vegan");
        }
        if(glutenFreeCheckBox.isSelected()){
            preferences.add("glutenFree");
        }
        if(dairyFreeCheckBox.isSelected()){
            preferences.add("dairyFree");
        }
        if(ketoCheckBox.isSelected()){
            preferences.add("keto");
        }
        if(paleoCheckBox.isSelected()){
            preferences.add("paleo");
        }
        if(lowSodiumCheckBox.isSelected()){
            preferences.add("lowSodium");
        }
        if(lowCarbCheckBox.isSelected()){
            preferences.add("lowCarb");
        }
        if(highProteinCheckBox.isSelected()){
            preferences.add("highProtein");
        }
        if(nutFreeCheckBox.isSelected()){
            preferences.add("nutFree");
        }

        // User object
        user = new User(
                firstNameTextField.getText(),
                lastNameTextField.getText(),
                emailTextField.getText(),
                hashedPassword,
                birthdayTextField.getText(),
                preferences
        );

        System.out.println("user: " + user.getFirstName() + " " + user.getLastName() + " - password: " + user.getPassword());
        System.out.println("food preferences: " + user.getPreferences());

        if (saveUserToDatabase(user)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Registration Successful");
            alert.setHeaderText(null);
            alert.setContentText("Your account has been created successfully!");
            alert.showAndWait();

            clearForm();
        }
    }

    private boolean saveUserToDatabase(User user) {
        String sql = "INSERT INTO users (username, email, password, first_name, last_name, birthday, preferences) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getFirstName());
            pstmt.setString(5, user.getLastName());
            pstmt.setString(6, user.getBirthday());

            String preferencesStr = String.join(",", user.getPreferences());
            pstmt.setString(7, preferencesStr);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to save user: " + e.getMessage());
            return false;
        }
    }

    private void clearForm() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        emailTextField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        birthdayTextField.clear();

        vegetarianCheckBox.setSelected(false);
        veganCheckBox.setSelected(false);
        glutenFreeCheckBox.setSelected(false);
        dairyFreeCheckBox.setSelected(false);
        ketoCheckBox.setSelected(false);
        paleoCheckBox.setSelected(false);
        lowSodiumCheckBox.setSelected(false);
        lowCarbCheckBox.setSelected(false);
        highProteinCheckBox.setSelected(false);
        nutFreeCheckBox.setSelected(false);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}