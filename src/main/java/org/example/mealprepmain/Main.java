package org.example.mealprepmain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/mealprepmain/homeScreen.fxml"));
        BorderPane root = fxmlLoader.load();
        Scene home = new Scene(root, 800 , 600);


        stage.setTitle("Meal Planning");
        stage.setScene(home);
        stage.show();

        HomeScreenController homeScreenController = fxmlLoader.getController();
        homeScreenController.setUsername("Nadeige Eugene");
    }

    public static void main(String[] args) {
        launch(args);
    }
}