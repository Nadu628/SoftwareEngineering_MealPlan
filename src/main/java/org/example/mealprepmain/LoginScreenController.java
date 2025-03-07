package org.example.mealprepmain;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginScreenController {

    private Stage stage;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginTitle;

    @FXML
    private ImageView sideImageGif;

    public void initialize(Stage stage) {
        this.stage = stage;
        Image stirfry = new Image(getClass().getResourceAsStream("/images/stirfry.gif"));
        sideImageGif.setImage(stirfry);

        sideImageGif.setFitWidth(245);
        sideImageGif.setFitHeight(190);
        sideImageGif.setPreserveRatio(false);


        stage.getScene().getStylesheets().add((getClass().getResource("/styles/loginStyles.css").toExternalForm()));
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
