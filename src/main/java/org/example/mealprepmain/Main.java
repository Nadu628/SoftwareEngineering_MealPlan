package org.example.mealprepmain;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try{
            System.out.println("Starting application with splash screen");

            //load splash screen
            FXMLLoader splashLoader= new FXMLLoader(getClass().getResource("/org/example/mealprepmain/splashScreen.fxml"));
            AnchorPane splashScreen = splashLoader.load();

            Scene splashScene = new Scene(splashScreen, 800 , 600);
            stage.setScene(splashScene);
            stage.setTitle("Welcome to Meal Prep Tech");
            stage.show();

            SplashScreenController splashScreenController = splashLoader.getController();
            splashScreenController.setStage(stage);


            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
            pauseTransition.setOnFinished(event -> splashScreenController.toLoginScreen());
            pauseTransition.play();
        } catch (IOException e){
            System.err.println("Error loading login screen");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}