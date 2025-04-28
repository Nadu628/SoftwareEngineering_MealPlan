package org.example.mealprepmain;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private String title;
    private List<String> ingredients; // could store ingredient names for now

    public Meal(String title, List<String> ingredients) {
        this.title = title;
        this.ingredients = new ArrayList<>(ingredients);
    }

    public String getTitle() {
        return title;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}
