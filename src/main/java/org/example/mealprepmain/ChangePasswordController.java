package org.example.mealprepmain;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.example.mealprepmain.database.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePasswordController {
    @FXML private PasswordField currentPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label usernameLabel;

    private int userId;
    private String username;
    private Stage stage;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
        if (usernameLabel != null) {
            usernameLabel.setText("Changing password for: " + username);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        if (username != null && usernameLabel != null) {
            usernameLabel.setText("Changing password for: " + username);
        }
    }

    @FXML
    private void handleChangePassword() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate inputs
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Error", "All fields must be filled");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "New passwords do not match");
            return;
        }

        if (newPassword.length() < 8) {
            showAlert(Alert.AlertType.WARNING, "Weak Password",
                    "Password must be at least 8 characters long");
            return;
        }

        try (Connection conn = Database.connect()) {
            // Verify current password
            String verifyQuery = "SELECT password FROM users WHERE id = ?";
            try (PreparedStatement verifyStmt = conn.prepareStatement(verifyQuery)) {
                verifyStmt.setInt(1, userId);
                ResultSet resultSet = verifyStmt.executeQuery();

                if (resultSet.next()) {
                    String storedHash = resultSet.getString("password");
                    if (!HandlePasswordHash.checkPassword(currentPassword, storedHash)) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Current password is incorrect");
                        return;
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "User not found");
                    return;
                }
            }

            // Hash and update new password
            String newHashedPassword = HandlePasswordHash.hashPassword(newPassword);
            String updateQuery = "UPDATE users SET password = ? WHERE id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setString(1, newHashedPassword);
                updateStmt.setInt(2, userId);

                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success",
                            "Password changed successfully. Returning to login screen.");
                    navigateToLoginScreen();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not update password");
        }
    }

    @FXML
    private void handleCancel() {
        navigateToLoginScreen();
    }

    private void navigateToLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/mealprepmain/loginScreen.fxml"));
            Scene loginScene = new Scene(loader.load(), 800, 600);
            LoginScreenController controller = loader.getController();
            controller.setStage(stage);
            stage.setScene(loginScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not return to login screen");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}