package org.example.mealprepmain;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginScreenController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    public void initialize() {
        System.out.println("Username: " + usernameField.getText()
        + "\nPassword: " + passwordField.getText());
    }

    @FXML
    private void handleLogin(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(validateCredentials(username, password)){
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + username + "!");
        }else{
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Please try again!");
        }
    }

    private boolean validateCredentials(String username, String password){
        return "username".equals(username) && "password".equals(password);

    }

    private void showAlert(Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
