package org.example.mealprepmain;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        //load homescreen

        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/org/example/mealprepmain/homeScreen.fxml"));
        Parent homeRoot = homeLoader.load();
        Scene scene = new Scene(homeRoot, 1000, 800);
        stage.setTitle("Meal Prep");
        stage.setScene(scene);
        stage.show();
        /*try{
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
        this is to load splash screen DO NOT DELETE ABOVE
         */

        //Test Spoonacular API
        /*RecipeServer server = new RecipeServer();
        try{
            String recipeJson = server.getRecipeInfo(123);
            System.out.println("Raw JSON: " + recipeJson);
        }catch(Exception e){
            e.printStackTrace();
        }

         */

    }

    public static void main(String[] args) {
        launch(args);
    }
}