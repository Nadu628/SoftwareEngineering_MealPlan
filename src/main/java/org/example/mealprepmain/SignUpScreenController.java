package org.example.mealprepmain;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignUpScreenController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        // At this point, stage already has a scene, so this is safe
        if (stage != null && stage.getScene() != null) {
            stage.getScene().getStylesheets().add(getClass().getResource("/org/example/mealprepmain/styles/signupStyle.css").toExternalForm());
        }
    }

    public void initialize(Stage stage) {
        System.out.println("Initialize");
    }



}
