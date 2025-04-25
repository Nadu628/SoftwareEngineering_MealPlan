package org.example.mealprepmain;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenController {
    @FXML
    public ScrollPane directionsScrollPane,ingredientScrollPane;
    @FXML
    private Label welcomeLabel, mealLabel;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton, clearButton, nextButton, previousButton;
    @FXML
    private Pane mealPane, ingredientsPane, calendarPane;
    @FXML
    private ImageView mealsIcon, ingredientsIcon, plannerIcon, mealImageView;
    @FXML
    TilePane ingredientsTilePane;
    @FXML
    private VBox directionsVBox;

    private RecipeServer recipeServer = new RecipeServer();
    private RecipeParser recipeParser = new RecipeParser();
    private List<Recipe> recipeList = new ArrayList<>();
    private int currentRecipeIndex = 0;

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

        ingredientsTilePane.prefWidthProperty().bind(ingredientsPane.widthProperty());
        ingredientsTilePane.setPadding(new Insets(10));
        ingredientsTilePane.setHgap(10);
        ingredientsTilePane.setVgap(10);

        directionsVBox.prefWidthProperty().bind(directionsScrollPane.widthProperty().subtract(20));
        directionsVBox.setSpacing(10);

        System.out.println("DirectionsVBox height: " + directionsVBox.getHeight());
        System.out.println("IngredientsTilePane height: " + ingredientsTilePane.getHeight());
        System.out.println("IngredientsScrollPane height: " + ingredientScrollPane.getHeight());
        System.out.println("DirectionsScrollPane height: " + directionsScrollPane.getHeight());
        searchButton.setOnAction(actionEvent -> handleSearch());
        clearButton.setOnAction(actionEvent -> clearMealPane());
        nextButton.setOnAction(actionEvent -> showNextRecipe());
        previousButton.setOnAction(actionEvent -> showPreviousRecipe());


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
        System.out.println("search text: " + searchText);
        if(searchText.isEmpty()){
            showAlert(Alert.AlertType.WARNING, "Empty Search", "Please search for a recipe");
            return;
        }
        try{
           String jsonResponse = recipeServer.searchRecipe(searchText, new User("John", "Doe", "john@gmail.com", "01/01/1999", List.of("vegetarien", "high-protein")));
           List<Recipe> basicRecipes = recipeParser.parseSearchResults(jsonResponse);
           System.out.println("Raw API response: " + jsonResponse);
           recipeList.clear();
           for(Recipe basicRecipe : basicRecipes){
               String detailedResponse = recipeServer.getRecipeInfo(basicRecipe.getId());
               Recipe detailedRecipe = recipeParser.parseRecipeDetails(detailedResponse);
                if (detailedRecipe != null){
                    recipeList.add(detailedRecipe);
                }
           }
           System.out.println("number of recipes: " + recipeList.size());
           if(!recipeList.isEmpty()){
               currentRecipeIndex = 0;
               populateMealPane(recipeList.get(currentRecipeIndex));
           }else{
               clearMealPane();
               mealLabel.setText("No meals found");
           }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Search Failed", "Unable to search for recipes");
            e.printStackTrace();
        }
    }

    private void populateMealPane(Recipe recipe){
        //set meal label
        System.out.println("populating meal pane with recipe");
        mealLabel.setText(recipe.getTitle());

        //set recipe image
        Image recipeImage = new Image(recipe.getImage());
        mealImageView.setImage(recipeImage);

        //populate instructions
        directionsVBox.getChildren().clear();
        ingredientsTilePane.getChildren().clear();
        if(recipe.getInstructions() != null){
            for(RecipeInstructions instructions: recipe.getInstructions()){
               if(instructions.getSteps() != null){
                   for(InstructionStep step: instructions.getSteps()){
                       Label stepLabel = new Label(step.getNumber() + "." + step.getStep());
                       stepLabel.setStyle("-fx-font-family: Monospaced; -fx-font-size: 13px;");
                       stepLabel.setWrapText(true);
                       stepLabel.setMaxWidth(600);
                       directionsVBox.getChildren().add(stepLabel);
                   }
               }
            }
        }else{
            Label noInstructionsLabel = new Label("No instructions found");
            directionsVBox.getChildren().add(noInstructionsLabel);
        }

        //populate ingredients
        if(recipe.getIngredients() != null){
            for(Ingredient ingredient : recipe.getIngredients()){
                CheckBox ingredientCheckBox = new CheckBox(ingredient.getName());
                ingredientCheckBox.setStyle("-fx-font-family: Monospaced; -fx-font-size: 13px");
                //Label ingredientLabel = new Label(ingredient.getName());
                ImageView ingredientIV = new ImageView(new Image("https://spoonacular.com/cdn/ingredients_100x100/" + ingredient.getImage()));
                ingredientIV.setFitWidth(50);
                ingredientIV.setPreserveRatio(true);
                VBox ingredientBox = new VBox(5);
                ingredientBox.getChildren().addAll(ingredientIV, ingredientCheckBox);
                ingredientsTilePane.getChildren().addAll(ingredientBox);
            }
        }else{
            Label noIngredientsLabel = new Label("No ingredients found");
            ingredientsTilePane.getChildren().add(noIngredientsLabel);
        }

    }

    private void clearMealPane(){
        mealLabel.setText("Search for a meal");
        mealImageView.setImage(null);
        directionsVBox.getChildren().clear();
        ingredientsTilePane.getChildren().clear();
        System.out.println("Meal pane cleared");
    }

    private void showNextRecipe(){
        if(currentRecipeIndex < recipeList.size() - 1){
            currentRecipeIndex++;
            populateMealPane(recipeList.get(currentRecipeIndex));
            System.out.println("showing next recipe: " + recipeList.get(currentRecipeIndex).getTitle() );
        }else{
            showAlert(Alert.AlertType.INFORMATION, "No more recipes", "Reached end of recipe list");
        }
    }

    private void showPreviousRecipe(){
        if(currentRecipeIndex > 0){
            currentRecipeIndex--;
            populateMealPane(recipeList.get(currentRecipeIndex));
            System.out.println("showing previous recipe: " + recipeList.get(currentRecipeIndex).getTitle() );
        }else{
            showAlert(Alert.AlertType.INFORMATION, "No previous recipes", "You are at the first recipe");
        }
    }

@FXML
    private void showMealsPane(){
    System.out.println("displaying meal pane");
        mealPane.setVisible(true);
        ingredientsPane.setVisible(false);
        calendarPane.setVisible(false);
    }

    @FXML
    private void showIngredientsPane(){
        System.out.println("displaying ingredients pane");
        ingredientsPane.setVisible(true);
        mealPane.setVisible(false);
        calendarPane.setVisible(false);
    }

    @FXML
    private void showCalendarPane(){
        System.out.println("displaying calendar pane");
        calendarPane.setVisible(true);
        mealPane.setVisible(false);
        ingredientsPane.setVisible(false);
    }


}
