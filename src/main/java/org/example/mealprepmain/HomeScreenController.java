package org.example.mealprepmain;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.example.mealprepmain.database.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HomeScreenController {

    @FXML
    private ScrollPane directionsScrollPane, ingredientScrollPane;
    @FXML
    private Label welcomeLabel, mealLabel, loadingLabel;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton, clearButton, nextButton, previousButton;
    @FXML
    private Button saveMealButton, removeIngredientsButton;
    @FXML
    private ImageView mealImageView, mealsIcon, ingredientsIcon, plannerIcon;
    @FXML
    private TilePane ingredientsTilePane;
    @FXML
    private VBox directionsVBox;
    @FXML
    private StackPane centerStack;
    @FXML
    private VBox mealPane, ingredientsPane, calendarPane;
    @FXML
    private ListView<String> savedMealsListView;
    @FXML
    private TilePane savedMealIngredientsTilePane;
    @FXML
    private ComboBox<String> daysComboBox;
    @FXML
    private Button assignMealButton;
    @FXML
    private ListView<String> calendarMealPlanListView;

    private RecipeServer recipeServer = new RecipeServer();
    private RecipeParser recipeParser = new RecipeParser();
    private List<Recipe> recipeList = new ArrayList<>();
    private List<Meal> savedMeals = new ArrayList<>();
    private int currentRecipeIndex = 0;
    private int userId;

    private final String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private final List<String> mealPlan = new ArrayList<>();

    public void initialize() {
        // Button Actions
        searchButton.setOnAction(event -> handleSearch());
        clearButton.setOnAction(event -> clearMealPane());
        nextButton.setOnAction(event -> showNextRecipe());
        previousButton.setOnAction(event -> showPreviousRecipe());
        saveMealButton.setOnAction(event -> saveCurrentMeal());
        removeIngredientsButton.setOnAction(event -> removeSelectedIngredients());

        // Icon Pane Switching
        mealsIcon.setOnMouseClicked(event -> showMealsPane());
        ingredientsIcon.setOnMouseClicked(event -> showIngredientsPane());
        plannerIcon.setOnMouseClicked(event -> showCalendarPane());

        savedMealsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showSavedMealInIngredientsPane(newSelection);
            }
        });

        ingredientsTilePane.setPadding(new Insets(10));
        ingredientsTilePane.setHgap(10);
        ingredientsTilePane.setVgap(10);
        directionsVBox.prefWidthProperty().bind(directionsScrollPane.widthProperty().subtract(20));
        directionsVBox.setSpacing(10);

        // Meal Plan Setup
        Database.createMealPlanTable();
        daysComboBox.getItems().addAll(daysOfWeek);
        for (int i = 0; i < 7; i++) mealPlan.add("");

        assignMealButton.setOnAction(event -> assignMealToDay());
    }

    public void setUsername(String username) {
        welcomeLabel.setText("Welcome " + username + "!");
    }

    public void setUserId(int userId) {
        this.userId = userId;
        loadSavedMeals();
        List<String> loadedPlan = Database.loadMealPlan(userId);
        mealPlan.clear();
        mealPlan.addAll(loadedPlan);
    }

    private void loadSavedMeals() {
        List<Meal> meals = Database.loadMeals(userId);
        savedMeals.clear();
        savedMealsListView.getItems().clear();

        for (Meal meal : meals) {
            savedMeals.add(meal);
            savedMealsListView.getItems().add(meal.getTitle());
        }
        System.out.println("Loaded " + meals.size() + " meals for user ID: " + userId);
    }

    private void assignMealToDay() {
        String selectedMeal = savedMealsListView.getSelectionModel().getSelectedItem();
        String selectedDay = daysComboBox.getValue();

        if (selectedMeal == null || selectedDay == null) {
            showAlert(Alert.AlertType.WARNING, "Incomplete Selection", "Please select a meal and a day!");
            return;
        }

        int dayIndex = -1;
        for (int i = 0; i < daysOfWeek.length; i++) {
            if (daysOfWeek[i].equals(selectedDay)) {
                dayIndex = i;
                break;
            }
        }

        if (dayIndex != -1) {
            mealPlan.set(dayIndex, selectedMeal);
            Database.saveMealPlan(userId, dayIndex, selectedMeal);
            showAlert(Alert.AlertType.INFORMATION, "Meal Assigned", "Assigned '" + selectedMeal + "' to " + selectedDay + ".");
        }
    }

    private void showMealsPane() {
        mealPane.setVisible(true);
        ingredientsPane.setVisible(false);
        calendarPane.setVisible(false);
    }

    private void showIngredientsPane() {
        mealPane.setVisible(false);
        ingredientsPane.setVisible(true);
        calendarPane.setVisible(false);
    }

    private void showCalendarPane() {
        mealPane.setVisible(false);
        ingredientsPane.setVisible(false);
        calendarPane.setVisible(true);

        updateCalendarView();
    }

    private void updateCalendarView() {
        calendarMealPlanListView.getItems().clear();
        for (int i = 0; i < daysOfWeek.length; i++) {
            String day = daysOfWeek[i];
            String meal = mealPlan.get(i);
            if (meal == null || meal.isEmpty()) {
                calendarMealPlanListView.getItems().add(day + ": No meal assigned");
            } else {
                calendarMealPlanListView.getItems().add(day + ": " + meal);
            }
        }
    }

    private void handleSearch() {
        String searchText = searchTextField.getText().trim();
        if (searchText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Empty Search", "Please enter a recipe to search for.");
            return;
        }
        loadingLabel.setVisible(true);

        CompletableFuture.runAsync(() -> {
            try {
                String jsonResponse = recipeServer.searchRecipe(searchText, new User(
                        "John", "Doe", "john@gmail.com", "PasswordTest", "01/01/1999", List.of("vegetarian", "high-protein")
                ));
                List<Recipe> basicRecipes = recipeParser.parseSearchResults(jsonResponse);

                List<Recipe> filteredRecipes = basicRecipes.stream()
                        .filter(recipe -> recipe.getTitle().toLowerCase().contains(searchText.toLowerCase()))
                        .toList();

                if (filteredRecipes.isEmpty()) {
                    javafx.application.Platform.runLater(() -> {
                        clearMealPane();
                        mealLabel.setText("No meals found.");
                        loadingLabel.setVisible(false);
                    });
                    return;
                }

                List<CompletableFuture<Recipe>> futures = filteredRecipes.stream()
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
                        mealLabel.setText("No meals found.");
                    }
                    loadingLabel.setVisible(false);
                });

            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
                    showAlert(Alert.AlertType.ERROR, "Search Failed", "Unable to complete search.");
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
            directionsVBox.getChildren().add(new Label("No instructions found."));
        }

        if (recipe.getIngredients() != null) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                VBox ingredientBox = new VBox(1);
                ingredientBox.setPadding(new Insets(1));
                ingredientBox.setStyle("-fx-alignment: center;");
                ingredientBox.setPrefWidth(140);
                ingredientBox.setPrefHeight(60);

                ImageView ingredientIV = new ImageView(new Image("https://spoonacular.com/cdn/ingredients_100x100/" + ingredient.getImage()));
                ingredientIV.setPreserveRatio(true);
                ingredientIV.setFitHeight(40);
                ingredientIV.setFitWidth(40);

                CheckBox ingredientCheckBox = new CheckBox(ingredient.getName());
                ingredientCheckBox.setWrapText(true);
                ingredientCheckBox.setMaxWidth(Double.MAX_VALUE);
                ingredientCheckBox.setStyle("-fx-font-family: Monospaced; -fx-font-size: 13px;");
                VBox.setVgrow(ingredientCheckBox, javafx.scene.layout.Priority.ALWAYS);

                ingredientBox.getChildren().addAll(ingredientIV, ingredientCheckBox);
                ingredientsTilePane.getChildren().add(ingredientBox);
            }
        } else {
            ingredientsTilePane.getChildren().add(new Label("No ingredients found."));
        }

        ingredientsTilePane.setTileAlignment(javafx.geometry.Pos.TOP_CENTER);
        ingredientsTilePane.setPrefTileWidth(150);
        ingredientsTilePane.setPrefTileHeight(170);
    }

    private void saveCurrentMeal() {
        if (mealLabel.getText() == null || mealLabel.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Meal", "No meal to save!");
            return;
        }

        List<String> currentIngredients = new ArrayList<>();
        for (Node node : ingredientsTilePane.getChildren()) {
            if (node instanceof VBox ingredientBox) {
                for (Node child : ingredientBox.getChildren()) {
                    if (child instanceof CheckBox checkBox) {
                        currentIngredients.add(checkBox.getText());
                    }
                }
            }
        }

        Meal savedMeal = new Meal(mealLabel.getText(), currentIngredients);
        savedMeals.add(savedMeal);
        savedMealsListView.getItems().add(savedMeal.getTitle());

        String ingredientsAsString = String.join(",", currentIngredients);
        Database.saveMeal(userId, savedMeal.getTitle(), ingredientsAsString);

        showAlert(Alert.AlertType.INFORMATION, "Meal Saved", "Meal '" + savedMeal.getTitle() + "' saved successfully!");
    }

    private void removeSelectedIngredients() {
        List<Node> toRemove = new ArrayList<>();

        for (Node node : ingredientsTilePane.getChildren()) {
            if (node instanceof VBox ingredientBox) {
                for (Node child : ingredientBox.getChildren()) {
                    if (child instanceof CheckBox checkBox && checkBox.isSelected()) {
                        toRemove.add(ingredientBox);
                        break;
                    }
                }
            }
        }
        ingredientsTilePane.getChildren().removeAll(toRemove);
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
            showAlert(Alert.AlertType.INFORMATION, "End of Recipes", "No more recipes available.");
        }
    }

    private void showPreviousRecipe() {
        if (currentRecipeIndex > 0) {
            currentRecipeIndex--;
            populateMealPane(recipeList.get(currentRecipeIndex));
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Start of Recipes", "You are at the first recipe.");
        }
    }

    private void showSavedMealInIngredientsPane(String mealTitle) {
        savedMealIngredientsTilePane.getChildren().clear();

        for (Meal meal : savedMeals) {
            if (meal.getTitle().equals(mealTitle)) {
                for (String ingredient : meal.getIngredients()) {
                    VBox ingredientBox = new VBox(5);
                    ingredientBox.setPadding(new Insets(5));
                    ingredientBox.setStyle("-fx-alignment: center;");
                    ingredientBox.setPrefWidth(120);
                    ingredientBox.setPrefHeight(100);

                    ImageView ingredientImage = new ImageView(new Image("https://spoonacular.com/cdn/ingredients_100x100/ingredient-placeholder.png"));
                    ingredientImage.setFitHeight(40);
                    ingredientImage.setFitWidth(40);
                    ingredientImage.setPreserveRatio(true);

                    Label ingredientLabel = new Label(ingredient);
                    ingredientLabel.setWrapText(true);
                    ingredientLabel.setStyle("-fx-font-family: Monospaced; -fx-font-size: 12px;");

                    ingredientBox.getChildren().addAll(ingredientImage, ingredientLabel);
                    savedMealIngredientsTilePane.getChildren().add(ingredientBox);
                }
                break;
            }
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
