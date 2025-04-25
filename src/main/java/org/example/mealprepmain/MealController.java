package org.example.mealprepmain;
/*
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealController {
    @FXML
    private Pane mealPane;
    @FXML
    private Label mealLabel;
    @FXML
    private ImageView mealImageView;
    @FXML
    private TilePane ingredientsTilePane;
    @FXML
    private VBox directionsVBox;
    @FXML
    private Button addMealButton, removeMealButton, searchButton, clearButton, nextButton, preciousButton;
    @FXML
    private TextField searchTextField;

    private RecipeServer recipeServer = new RecipeServer(); //instance of RecipeServer
    private RecipeParser recipeParser = new RecipeParser(); //instance of RecipeParser
    private IngredientsController ingredientsController;
    private List<Recipe> recipeList = new ArrayList<>();
    private int currentRecipeIndex = 0;
    private User currentUser = new User("John", "Doe", "john@gmail.com", "01/01/1999", Arrays.asList("vegetarian", "low-carv"));

    @FXML
    public void initialize(){
        searchButton.setOnAction(event -> {
            String search = searchTextField.getText(); //get text from searchTextField
            try{
                //fetch recipes based on preferences
                String jsonResponse = recipeServer.searchRecipe(search, currentUser);
                recipeList = recipeParser.parseSearchResults(jsonResponse);

                if(!recipeList.isEmpty()){
                    //populate meal pane with first recipe
                    currentRecipeIndex = 0;
                    populateMealPane(recipeList.get(currentRecipeIndex));
                }else{
                    clearMealPane();
                    mealLabel.setText("No meals found");
                }
            }catch(Exception e){
                clearMealPane();
                mealLabel.setText("Error fetching recipes");
                e.printStackTrace();
            }
        });

        clearButton.setOnAction(event -> clearMealPane());

        nextButton.setOnAction(event -> showNextRecipe());
        preciousButton.setOnAction(event -> showPreviousRecipe());

        addMealButton.setOnAction(event -> addSelectedIngredientsToShoppingList());
    }

    public void handleSearch(String searchText) throws Exception{
        String jsonResponse = recipeServer.searchRecipe(searchText, currentUser);
        recipeList = recipeParser.parseSearchResults(jsonResponse);

        if(!recipeList.isEmpty()){
            currentRecipeIndex = 0;
            populateMealPane(recipeList.get(currentRecipeIndex));
        }else{
            clearMealPane();
            mealLabel.setText("No meals found");
        }
    }

    private void populateMealPane(Recipe recipe){
        //set meal label
        mealLabel.setText(recipe.getTitle());

        //set recipe image
        Image recipeImage = new Image(recipe.getImage());
        mealImageView.setImage(recipeImage);

        //populate instructions
        directionsVBox.getChildren().clear();
        for(InstructionStep step : recipe.getInstructions().getInstructions()){
            HBox stepBox = new HBox(10);
            Label stepLabel = new Label(step.getNumber() + ". " + step.getStep());
            stepLabel.setStyle("-fx-font-family: monospace; -fx-font-size: 13px;");
            stepBox.getChildren().add(stepLabel);
            directionsVBox.getChildren().add(stepBox);
        }

        //populate ingredients
        ingredientsTilePane.getChildren().clear();
        for(Ingredient ingredient : recipe.getIngredients()){
            VBox ingredientBox = new VBox(5);
            Label ingredientLabel = new Label(ingredient.getName());
            ImageView ingredientIV = new ImageView(new Image("https://spoonacular.com/cdn/ingredients_100x100/" + ingredient.getImage()));
            ingredientIV.setFitWidth(50);
            ingredientIV.setPreserveRatio(true);
            ingredientBox.getChildren().addAll(ingredientIV, ingredientLabel);
            ingredientsTilePane.getChildren().add(ingredientBox);
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
        }else{
            System.out.println("No more recipes to display");
        }
    }

    private void showPreviousRecipe(){
        if(currentRecipeIndex > 0){
            currentRecipeIndex--;
            populateMealPane(recipeList.get(currentRecipeIndex));
        }else{
            System.out.println("No previous recipes to display");
        }
    }

    private void addSelectedIngredientsToShoppingList(){
        //iterate through children of ingredients TilePane
        for(Node node: ingredientsTilePane.getChildren()){
            if(node instanceof VBox){
                VBox ingredientBox = (VBox) node;
                Ingredient ingredient = new Ingredient();

                for(Node child: ingredientBox.getChildren()){
                    if(child instanceof Label){
                        ingredient.setName(((Label) child).getText());
                    }else if(child instanceof ImageView){
                        ImageView imageView = (ImageView) child;
                        String imageUrl = imageView.getImage().getUrl();
                        ingredient.setImage(imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
                    }
                }
                ingredientsController.addToShoppingList(ingredient);
            }
        }
        System.out.println("ingredients added to shopping list");
    }

    public void setIngredientsController(IngredientsController ingredientsController){
        this.ingredientsController = ingredientsController;
    }

    public String searchForRecipe(String searchText) throws Exception {
        return recipeServer.searchRecipe(searchText, currentUser);
    }

    public void displaySearchResult(String jsonResponse) throws Exception {
        recipeList = recipeParser.parseSearchResults(jsonResponse);
        if(!recipeList.isEmpty()){
            currentRecipeIndex = 0;
            populateMealPane(recipeList.get(currentRecipeIndex));
        }else{
            clearMealPane();
            mealLabel.setText("No recipes found");
        }
    }


}

 */
