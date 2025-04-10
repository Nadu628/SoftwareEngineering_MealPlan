package org.example.mealprepmain;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Tooltip;

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
    @FXML
    private ImageView mealsIcon, ingredientsIcon, plannerIcon;


    public void initialize(){
        //welcomeLabel.setText("Welcome " + username + "!");
        showMealsPane();

        //add click event handlers
        mealsIcon.setOnMouseClicked(mouseEvent -> {
            showMealsPane();
            System.out.println("meal icon clicked");
        });
        ingredientsIcon.setOnMouseClicked(mouseEvent -> {
            showIngredientsPane();
            System.out.println("ingredients icon clicked");
        });
        plannerIcon.setOnMouseClicked(mouseEvent -> {
            showCalendarPane();
            System.out.println("calendar icon clicked");
        });

        //add hover explanations
        Tooltip.install(mealsIcon, new Tooltip("View and manage your meals"));
        Tooltip.install(ingredientsIcon, new Tooltip("See and organize your ingredients"));
        Tooltip.install(plannerIcon, new Tooltip("Plan your weekly meals"));

    }

    public void setUsername(String username){
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

    @FXML
    private void showMealsPane(){
        mealPane.setVisible(true);
        ingredientsPane.setVisible(false);
        calendarPane.setVisible(false);
    }

    @FXML
    private void showIngredientsPane(){
        ingredientsPane.setVisible(true);
        mealPane.setVisible(false);
        calendarPane.setVisible(false);
    }

    @FXML
    private void showCalendarPane(){
        calendarPane.setVisible(true);
        mealPane.setVisible(false);
        ingredientsPane.setVisible(false);
    }


}
