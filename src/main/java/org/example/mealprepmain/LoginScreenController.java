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

import java.io.IOException;
import java.sql.*;

public class LoginScreenController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        System.out.println("stage reference set in login screen controller");
    }

    @FXML
    private TextField usernameTF, passwordTF;

    @FXML
    private Button registerButton, createNewPasswordButton, loginButton;

    @FXML
    private ImageView loginScreenIV;

    //Placeholder credentials
    private static final String USERNAME = "user1";
    private static final String EMAIL = "user1@example.com";
    private static final String PASSWORD = HandlePasswordHash.hashPassword("password"); //hashed password

    @FXML
    public void initialize() {
        System.out.println("Initializing Login Screen");
        loginButton.setOnAction(event -> handleLogin());
        registerButton.setOnAction(event -> navigateToRegistration());
    }

    @FXML
    private void handleLogin(){
        //debug
        System.out.println("handle login invoked");

        String username = usernameTF.getText();
        String password = passwordTF.getText();

        //debug
        System.out.println("username: " + username);
        System.out.println("password: " + password);

        //basic validation
        if(username.isEmpty() || password.isEmpty()){
            showAlert(Alert.AlertType.WARNING, "Error", "Please enter all the fields");
            return;
        }
        //authentication - change from validateCredentials to validateDatabaseCredentials
        if(validateDatabaseCredentials(username, password)){
            try{
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + username + "!");
                navigateToHomeScreen();
            } catch (Exception e){
                e.printStackTrace();
            }
        }else{
            showAlert(Alert.AlertType.ERROR, "Login Failed",  "Please try again");
        }
    }
    private boolean validateCredentials(String username, String password){
        return (USERNAME.equals(username) || EMAIL.equals(username)) && HandlePasswordHash.checkPassword(password, PASSWORD);
    }

    private boolean validateDatabaseCredentials(String username, String password) {
        try (Connection conn = Database.connect()) {
            String query = "SELECT password FROM users WHERE username = ? OR email = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String storedPasswordHash = resultSet.getString("password");
                return HandlePasswordHash.checkPassword(password, storedPasswordHash);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while validating credentials");
        }
        return false;
    }

    @FXML
    private void navigateToHomeScreen(){
        try{
            FadeTransition fadeout = new FadeTransition(Duration.seconds(1),stage.getScene().getRoot());
            fadeout.setFromValue(1.0);
            fadeout.setToValue(0.0);
            fadeout.setOnFinished(event ->{
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/mealprepmain/homeScreen.fxml"));
                    BorderPane homeScreen = loader.load();

                    HomeScreenController controller = loader.getController();
                    controller.setUsername(usernameTF.getText());

                    //get current stage and set new scene
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    Scene homeScene = new Scene(homeScreen, 800, 600);
                    stage.setScene(homeScene);

                    FadeTransition fadein = new FadeTransition(Duration.seconds(1),homeScene.getRoot());
                    fadein.setFromValue(0.0);
                    fadein.setToValue(1.0);
                    fadein.play();
                    System.out.println("transitioned to home screen");
                }catch(IOException e){
                    e.printStackTrace();
                    System.err.println("Error transitioning to home screen");
                }
            });
            fadeout.play();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "An error occured while navigating to the home screen");
        }

    }

    @FXML
    private void navigateToRegistration(){
        try{
            FadeTransition fadeout = new FadeTransition(Duration.seconds(1),stage.getScene().getRoot());
            fadeout.setFromValue(1.0);
            fadeout.setToValue(0.0);
            fadeout.setOnFinished(event ->{
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/mealprepmain/registrationScreen.fxml"));
                    AnchorPane registrationScreen = loader.load();

                    Scene registrationScene = new Scene(registrationScreen, 800, 600);
                    stage.setScene(registrationScene);

                    FadeTransition fadein = new FadeTransition(Duration.seconds(1),registrationScene.getRoot());
                    fadein.setFromValue(0.0);
                    fadein.setToValue(1.0);
                    fadein.play();
                    System.out.println("transitioned to registration screen");
                }catch(IOException e){
                    e.printStackTrace();
                    System.err.println("Error transitioning to registration screen");
                }
            });
            fadeout.play();
        }catch(Exception e){
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "An error occured while navigating to the registration screen");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
