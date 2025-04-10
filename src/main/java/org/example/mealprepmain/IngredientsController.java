package org.example.mealprepmain;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class IngredientsController {
    @FXML
    private Pane ingredientsPane, shoppingListPane, ingredientInfoPane;
    @FXML
    private Label nutritionFactsLabel, ingredientLabel, caloriesLabel, proteinLabel, fatLabel, carbsLabel, sugarLabel, fiberLabel;
    @FXML
    private Button addToListButton, removeFromListButton, ingredientSearchButton;
    @FXML
    private TextField ingredientSearchBarTextField;
}
