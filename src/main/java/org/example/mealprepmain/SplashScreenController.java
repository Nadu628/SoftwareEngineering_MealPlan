package org.example.mealprepmain;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SplashScreenController {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize(Stage stage) {
        System.out.println("SplashScreenController initialized");

    }

    public void toLoginScreen() {
        try{
            FadeTransition fadeout = new FadeTransition(Duration.seconds(1), stage.getScene().getRoot());
            fadeout.setFromValue(1.0);
            fadeout.setToValue(0.0);
            fadeout.setOnFinished(event -> {
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/mealprepmain/loginScreen.fxml"));
                    AnchorPane loginScreen = fxmlLoader.load();

                    LoginScreenController loginScreenController = fxmlLoader.getController();
                    loginScreenController.setStage(stage);

                    if(stage == null){
                        throw new IllegalStateException("stage reference is null");
                    }
                    Scene scene = new Scene (loginScreen, 800, 600);
                    stage.setScene(scene);

                    FadeTransition fadein = new FadeTransition(Duration.seconds(1),scene.getRoot());
                    fadein.setFromValue(0.0);
                    fadein.setToValue(1.0);
                    fadein.play();
                    System.out.println("transitioned to log in screen");

                }catch(Exception e){
                    e.printStackTrace();
                    System.err.println("Failed to load login screen");
                }
            });
            fadeout.play();
        }catch(Exception e) {
            e.printStackTrace();
            System.err.println("SplashScreenController could not load login screen");
        }
    }
}