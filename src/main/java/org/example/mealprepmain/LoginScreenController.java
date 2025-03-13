package org.example.mealprepmain;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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

    @FXML
    private Button signUpButton;

    public void initialize(Stage stage) {
        this.stage = stage;
        Image stirfry = new Image(getClass().getResourceAsStream("/images/stirfry.gif"));
        sideImageGif.setImage(stirfry);

        sideImageGif.setFitWidth(245);
        sideImageGif.setFitHeight(190);
        sideImageGif.setPreserveRatio(false);

        stage.getScene().getStylesheets().add((getClass().getResource("/styles/loginStyle.css").toExternalForm()));
    }

    @FXML
    private void handleLogin(){
        String username = usernameField.getText();
        String password = passwordField.getText();
        if(validateCredentials(username, password)){
            try{
                FXMLLoader homescreenLoader = new FXMLLoader(getClass().getResource("/fxml/homeScreen.fxml"));
                Scene homeScene = new Scene(homescreenLoader.load());

                stage.setScene(homeScene);
                stage.setTitle("Home");
            } catch (Exception e){
                e.printStackTrace();
            }
        }else{
            showAlert(Alert.AlertType.ERROR, "Login Failed",  "Please try again");
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
