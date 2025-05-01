package org.example.mealprepmain;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.mealprepmain.database.Database;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.sql.*;

public class LoginScreenController {

    private Stage stage;
    private int authenticatedUserId = -1;

    @FXML
    private TextField usernameTF;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private Button createNewPasswordButton, loginButton, signUpButton;

    @FXML
    private ImageView loginScreenIV;

    private static final String USERNAME = "user1";
    private static final String EMAIL = "user1@example.com";
    private static final String PASSWORD = HandlePasswordHash.hashPassword("password");

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> handleLogin());
        signUpButton.setOnAction(event -> navigateToRegistration());
        createNewPasswordButton.setOnAction(event -> navigateToChangePassword());
    }

    @FXML
    private void handleLogin() {
        String username = usernameTF.getText();
        String password = passwordTF.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Error", "Please enter all fields");
            return;
        }

        if (validateDatabaseCredentials(username, password)) {
            try {
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + username + "!");
                navigateToHomeScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Please try again");
        }
    }

    private boolean validateDatabaseCredentials(String username, String password) {
        try (Connection conn = Database.connect()) {
            String query = "SELECT id, password FROM users WHERE username = ? OR email = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String storedPasswordHash = resultSet.getString("password");
                if (HandlePasswordHash.checkPassword(password, storedPasswordHash)) {
                    authenticatedUserId = resultSet.getInt("id");
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while validating credentials");
        }
        return false;
    }

    @FXML
    private void navigateToHomeScreen() {
        try {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), stage.getScene().getRoot());
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/mealprepmain/homeScreen.fxml"));
                    BorderPane homeScreen = loader.load();
                    HomeScreenController controller = loader.getController();
                    controller.setUsername(usernameTF.getText());
                    controller.setUserId(authenticatedUserId);

                    Stage currentStage = (Stage) loginButton.getScene().getWindow();
                    Scene homeScene = new Scene(homeScreen, 1000, 800);
                    currentStage.setScene(homeScene);
                    currentStage.setMaximized(true);

                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), homeScene.getRoot());
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "An error occurred while navigating to the Home Screen");
        }
    }

    @FXML
    private void navigateToRegistration() {
        try {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), stage.getScene().getRoot());
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/mealprepmain/registrationScreen.fxml"));
                    AnchorPane registrationScreen = loader.load();
                    Scene registrationScene = new Scene(registrationScreen, 800, 600);
                    stage.setScene(registrationScene);

                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), registrationScene.getRoot());
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "An error occurred while navigating to the Registration Screen");
        }
    }
    @FXML
    private void navigateToChangePassword() {
        String username = usernameTF.getText();
        if (username.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Error", "Please enter your username first");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/mealprepmain/passwordChangeScreen.fxml"));
            AnchorPane changePasswordScreen = loader.load();
            ChangePasswordController controller = loader.getController();
            controller.setStage(stage);
            controller.setUserId(authenticatedUserId);
            controller.setUsername(username);

            Scene changePasswordScene = new Scene(changePasswordScreen, 600, 400);
            stage.setScene(changePasswordScene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load change password screen");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
