package org.example.mealprepmain;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignUpScreenController {

    private Stage stage;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button createAccountButton;

    @FXML
    private Button backToLoginButton;
    public void initialize(Stage stage) {
        System.out.println("Initialize");
    }
    public void setStage(Stage stage) {
        this.stage = stage;

        if (stage != null && stage.getScene() != null) {
            stage.getScene().getStylesheets().add(getClass().getResource("/org/example/mealprepmain/styles/signupStyle.css").toExternalForm());
        }
    }

    @FXML
    private void handleCreateAccount() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Error Handling for field related issues
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "All fields are required");
            return;
        }

        if (password.length() < 6) {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "Password must be at least 6 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "Passwords do not match");
            return;
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
