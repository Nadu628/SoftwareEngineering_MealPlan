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

}
