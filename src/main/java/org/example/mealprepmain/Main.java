package org.example.mealprepmain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// Import Database Class
import org.example.mealprepmain.database.Database;
import java.sql.Connection;

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

        Connection connection = Database.connect();

        if (connection != null) {
            // Continue with the rest of your logic
            System.out.println("Database connection successful!");
        } else {
            System.out.println("Database connection failed.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}