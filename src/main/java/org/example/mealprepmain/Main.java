package org.example.mealprepmain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("splashScreen.fxml"));
        Scene splashScreen = new Scene(fxmlLoader.load(), 320, 240);

        stage.setScene(splashScreen);
        stage.setTitle("Meal Planning");
        stage.show();

        SplashScreenController splashScreenController = fxmlLoader.getController();
        splashScreenController.init(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}