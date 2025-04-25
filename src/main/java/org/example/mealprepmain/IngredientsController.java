package org.example.mealprepmain;
/*
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.mealprepmain.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsController {
    @FXML
    private Pane ingredientsPane, shoppingListPane, ingredientInfoPane;
    @FXML
    private Label nutritionFactsLabel, ingredientLabel, caloriesLabel, proteinLabel, fatLabel, carbsLabel, sugarLabel, fiberLabel;
    @FXML
    private Button addToListButton, removeFromListButton, ingredientSearchButton;
    @FXML
    private TextField ingredientSearchBarTextField;
    private List<Ingredient> shoppingList = new ArrayList<>();

    public void addToShoppingList(Ingredient ingredient) {
        shoppingList.add(ingredient);

        //update UI
        VBox ingredientsBox = new VBox(5); //hold ingredients
        ingredientsBox.setStyle("-fx-padding: 5; -fx-border-color: black;");

        Label ingredientsLabel = new Label(ingredient.getName());
        ingredientsLabel.setStyle("-fx-font-size: 13px;");

        ImageView ingredientImage = new ImageView(new Image("https://spoonacular.com/cdn/ingredients_100x100/" + ingredient.getImage()));
        ingredientImage.setFitWidth(50);
        ingredientImage.setPreserveRatio(true);

        CheckBox ingredientCheckBox = new CheckBox();
        ingredientsBox.getChildren().addAll(ingredientImage, ingredientsLabel, ingredientCheckBox);
        shoppingListPane.getChildren().add(ingredientsBox);
        System.out.println("added to shopping list" + ingredient.getName());

    }

    public void removeCheckedIngredients() {
        List<Ingredient> itemsToRemove = new ArrayList<>();

        for(Node node : shoppingListPane.getChildren()) {
            if(node instanceof VBox){
                VBox ingredientsBox = (VBox) node;
                CheckBox checkBox = (CheckBox) ingredientsBox.getChildren().get(0);
                if(checkBox.isSelected()){
                    Label label = (Label) ingredientsBox.getChildren().get(2);
                    String name = label.getText();
                    for(Ingredient ingredient : shoppingList){
                        if(ingredient.getName().equals(name)){
                            itemsToRemove.add(ingredient);
                            break;
                        }
                    }
                }
            }
        }
        shoppingList.removeAll(itemsToRemove);
        refreshShoppingList();
        System.out.println("removed ingredients from shopping list");

    }

    private void refreshShoppingList() {
        shoppingListPane.getChildren().clear();
        for(Ingredient ingredient : shoppingList){
            addToShoppingList(ingredient);
        }
    }
}

 */
