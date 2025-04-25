package org.example.mealprepmain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Recipe {
    @SerializedName("id")
    private int id; //recipe id from spoonacular
    @SerializedName("title")
    private String title; //recipe title
    @SerializedName("extendedIngredients")
    private List<Ingredient> ingredients; //list of ingredients
    @SerializedName("analyzedInstructions")
    private List<RecipeInstructions> instructions; //instructions for recipe
    @SerializedName("image")
    private String image; //url for image

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<RecipeInstructions> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<RecipeInstructions> instructions) {
        this.instructions = instructions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Recipe{"+
                "id: " + id +
                "\ntitle: " + title  +
                "\ningredients: " + (ingredients != null ? ingredients.size(): "null") +
                "\ninstructions: " + (instructions != null ? instructions.size(): "null") +
                "}";
    }
}
