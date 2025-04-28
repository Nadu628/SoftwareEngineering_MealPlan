package org.example.mealprepmain;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HomeScreenController {
    @FXML
    private ScrollPane directionsScrollPane, ingredientScrollPane;
    @FXML
    private Label welcomeLabel, mealLabel;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton, clearButton, nextButton, previousButton;
    @FXML
    private ImageView mealImageView;
    @FXML
    private TilePane ingredientsTilePane;
    @FXML
    private VBox directionsVBox;
    @FXML
    private Label loadingLabel;

    private RecipeServer recipeServer = new RecipeServer();
    private RecipeParser recipeParser = new RecipeParser();
    private List<Recipe> recipeList = new ArrayList<>();
    private int currentRecipeIndex = 0;

    public void initialize() {
        ingredientsTilePane.setPadding(new Insets(10));
        ingredientsTilePane.setHgap(10);
        ingredientsTilePane.setVgap(10);

        directionsVBox.prefWidthProperty().bind(directionsScrollPane.widthProperty().subtract(20));
        directionsVBox.setSpacing(10);

        searchButton.setOnAction(actionEvent -> handleSearch());
        clearButton.setOnAction(actionEvent -> clearMealPane());
        nextButton.setOnAction(actionEvent -> showNextRecipe());
        previousButton.setOnAction(actionEvent -> showPreviousRecipe());
    }

    public void setUsername(String username) {
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome " + username + "!");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void handleSearch() {
        String searchText = searchTextField.getText();
        if (searchText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Empty Search", "Please search for a recipe");
            return;
        }
        loadingLabel.setVisible(true);

        CompletableFuture.runAsync(() -> {
            try {
                String jsonResponse = recipeServer.searchRecipe(searchText, new User(
                        "John", "Doe", "john@gmail.com", "PasswordTest", "01/01/1999", List.of("vegetarien", "high-protein")
                ));
                List<Recipe> basicRecipes = recipeParser.parseSearchResults(jsonResponse);

                if (basicRecipes.isEmpty()) {
                    javafx.application.Platform.runLater(() -> {
                        clearMealPane();
                        mealLabel.setText("No meals found");
                        loadingLabel.setVisible(false);
                    });
                    return;
                }

                List<CompletableFuture<Recipe>> futures = basicRecipes.stream()
                        .map(recipe -> CompletableFuture.supplyAsync(() -> {
                            try {
                                String detailedJson = recipeServer.getRecipeInfo(recipe.getId());
                                return recipeParser.parseRecipeDetails(detailedJson);
                            } catch (Exception e) {
                                e.printStackTrace();
                                return null;
                            }
                        }))
                        .toList();

                List<Recipe> detailedRecipes = futures.stream()
                        .map(CompletableFuture::join)
                        .filter(recipe -> recipe != null)
                        .toList();

                javafx.application.Platform.runLater(() -> {
                    recipeList.clear();
                    recipeList.addAll(detailedRecipes);

                    if (!recipeList.isEmpty()) {
                        currentRecipeIndex = 0;
                        populateMealPane(recipeList.get(currentRecipeIndex));
                    } else {
                        clearMealPane();
                        mealLabel.setText("No meals found");
                    }
                    loadingLabel.setVisible(false);
                });

            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
                    showAlert(Alert.AlertType.ERROR, "Search Failed", "Unable to search for recipes");
                    loadingLabel.setVisible(false);
                });
                e.printStackTrace();
            }
        });
    }

    private void populateMealPane(Recipe recipe) {
        mealLabel.setText(recipe.getTitle());
        mealImageView.setImage(new Image(recipe.getImage()));
        directionsVBox.getChildren().clear();
        ingredientsTilePane.getChildren().clear();

        if (recipe.getInstructions() != null) {
            for (RecipeInstructions instructions : recipe.getInstructions()) {
                if (instructions.getSteps() != null) {
                    for (InstructionStep step : instructions.getSteps()) {
                        Label stepLabel = new Label(step.getNumber() + ". " + step.getStep());
                        stepLabel.setStyle("-fx-font-family: Monospaced; -fx-font-size: 13px;");
                        stepLabel.setWrapText(true);
                        stepLabel.setMaxWidth(600);
                        directionsVBox.getChildren().add(stepLabel);
                    }
                }
            }
        } else {
            Label noInstructionsLabel = new Label("No instructions found");
            directionsVBox.getChildren().add(noInstructionsLabel);
        }

        if (recipe.getIngredients() != null) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                VBox ingredientBox = new VBox(5);
                ingredientBox.setPadding(new Insets(5));
                ingredientBox.setStyle("-fx-alignment: center;");
                ingredientBox.setPrefWidth(140);
                ingredientBox.setPrefHeight(160);

                ImageView ingredientIV = new ImageView(new Image("https://spoonacular.com/cdn/ingredients_100x100/" + ingredient.getImage()));
                ingredientIV.setPreserveRatio(true);
                ingredientIV.setFitHeight(80);
                ingredientIV.setFitWidth(80);

                CheckBox ingredientCheckBox = new CheckBox(ingredient.getName());
                ingredientCheckBox.setWrapText(true);
                ingredientCheckBox.setMaxWidth(Double.MAX_VALUE);
                ingredientCheckBox.setStyle("-fx-font-family: Monospaced; -fx-font-size: 13px;");
                VBox.setVgrow(ingredientCheckBox, javafx.scene.layout.Priority.ALWAYS);

                ingredientBox.getChildren().addAll(ingredientIV, ingredientCheckBox);
                ingredientsTilePane.getChildren().add(ingredientBox);
            }
        } else {
            Label noIngredientsLabel = new Label("No ingredients found");
            ingredientsTilePane.getChildren().add(noIngredientsLabel);
        }

        ingredientsTilePane.setTileAlignment(javafx.geometry.Pos.TOP_CENTER);
        ingredientsTilePane.setHgap(10);
        ingredientsTilePane.setVgap(10);
        ingredientsTilePane.setPadding(new Insets(10));
        ingredientsTilePane.setPrefTileWidth(150);
        ingredientsTilePane.setPrefTileHeight(170);

        ingredientsTilePane.widthProperty().addListener((obs, oldVal, newVal) -> {
            int columns = (int) (newVal.doubleValue() / 180);
            ingredientsTilePane.setPrefColumns(Math.max(columns, 1));
        });
    }

    private void clearMealPane() {
        mealLabel.setText("Search for a meal");
        mealImageView.setImage(null);
        directionsVBox.getChildren().clear();
        ingredientsTilePane.getChildren().clear();
    }

    private void showNextRecipe() {
        if (currentRecipeIndex < recipeList.size() - 1) {
            currentRecipeIndex++;
            populateMealPane(recipeList.get(currentRecipeIndex));
        } else {
            showAlert(Alert.AlertType.INFORMATION, "No more recipes", "Reached end of recipe list");
        }
    }

    private void showPreviousRecipe() {
        if (currentRecipeIndex > 0) {
            currentRecipeIndex--;
            populateMealPane(recipeList.get(currentRecipeIndex));
        } else {
            showAlert(Alert.AlertType.INFORMATION, "No previous recipes", "You are at the first recipe");
        }
    }
}
