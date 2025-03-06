package org.example.mealprepmain;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SplashScreenController {
    private Stage stage;

    public void init(Stage stage) {
        this.stage = stage;

        new Thread(() -> {
            try {
                Thread.sleep(3000); //3 seconds delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(this::showLoginScreen);
        }).start();
    }

    private void showLoginScreen() {
        FadeTransition fadeout = new FadeTransition(Duration.seconds(1), stage.getScene().getRoot());
        fadeout.setFromValue(1);
        fadeout.setToValue(0);
        fadeout.setOnFinished(event -> {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginScreen.fxml"));
                Scene loginScene = new Scene(fxmlLoader.load());
                stage.setScene(loginScene);
                stage.setTitle("Login");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        fadeout.play();

    }
}