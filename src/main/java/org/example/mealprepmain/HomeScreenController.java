package org.example.mealprepmain;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class HomeScreenController {
    //home screen initial UI features
    @FXML
    private Label welcomeLabel, searchLabel;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton;
    @FXML
    private Pane mealPane, ingredientsPane, calendarPane;

    //meal section, includes recipe, nutrition facts, add or remove button
    @FXML
    private ImageView mealImageView;
    @FXML
    private TextArea nutritionFactsTextArea, recipeTextArea;
    @FXML
    private Button addMealButton, removeMealButton;

    public void initialiazeUser(String username){
        welcomeLabel.setText("Welcome " + username + "!");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleSearch(){
        String searchText = searchTextField.getText();
        if(searchText.equals("")){
            showAlert(Alert.AlertType.WARNING, "Empty Search", "Please search for a recipe");
        }
    }


}
