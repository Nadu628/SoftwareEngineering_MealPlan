package org.example.mealprepmain;
import com.google.gson.*;

import java.lang.classfile.Instruction;
import java.util.ArrayList;
import java.util.List;

public class RecipeParser {

    public List<Recipe> parseSearchResults(String jsonResponse) {
        try {
            System.out.println("Raw json response: " + jsonResponse);
            JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);
            JsonArray results = jsonObject.getAsJsonArray("results");
            List<Recipe> recipeList = new ArrayList<>();
            for (JsonElement element : results) {
                Recipe recipe = new Gson().fromJson(element, Recipe.class);
                System.out.println("parsed recipe: " + recipe.getTitle());
                recipeList.add(recipe);
            }
            System.out.println("number of recipes: " + recipeList.size());
            return recipeList;
        }catch(Exception e){
            System.err.println("error parsing json response");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /*public List<Recipe> parseRecipeDetails(String jsonResponse) {
        try{
            System.out.println("Raw json response: " + jsonResponse);
            JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);
           JsonArray results = jsonObject.getAsJsonArray("results");
            List<Recipe> recipeList = new ArrayList<>();
            for(JsonElement element : results){
                Recipe recipe = new Gson().fromJson(element, Recipe.class);
                System.out.println("parsed recipe: " + recipe.getTitle());
                recipeList.add(recipe);
            }
            System.out.println("number of recipes: " + recipeList.size());
            return recipeList;
        }catch(Exception e){
            System.err.println("error parsing json response");
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

     */

    public Recipe parseRecipeDetails(String jsonResponse) {
        try {
            JsonObject recipeObj = JsonParser.parseString(jsonResponse).getAsJsonObject();

            int id = recipeObj.get("id").getAsInt();
            String title = recipeObj.get("title").getAsString();
            String image = recipeObj.get("image").getAsString();

            List<RecipeInstructions> instructions = new ArrayList<>();
            if (recipeObj.has("analyzedInstructions")) {  // <-- FIXED from instructions
                JsonArray instructionsArray = recipeObj.getAsJsonArray("analyzedInstructions");
                for (JsonElement instr : instructionsArray) {
                    JsonObject instrObj = instr.getAsJsonObject();
                    if (instrObj.has("steps")) {
                        List<InstructionStep> steps = new ArrayList<>();
                        JsonArray stepsArray = instrObj.getAsJsonArray("steps");
                        for (JsonElement stepElement : stepsArray) {
                            JsonObject stepObj = stepElement.getAsJsonObject();
                            int number = stepObj.get("number").getAsInt();
                            String step = stepObj.get("step").getAsString();
                            steps.add(new InstructionStep(number, step));
                        }
                        instructions.add(new RecipeInstructions(steps));
                    }
                }
            }

            List<Ingredient> ingredients = new ArrayList<>();
            if (recipeObj.has("extendedIngredients")) {
                JsonArray ingredientsArray = recipeObj.getAsJsonArray("extendedIngredients");
                for (JsonElement ingredientElement : ingredientsArray) {
                    JsonObject ingredientObj = ingredientElement.getAsJsonObject();
                    String name = ingredientObj.get("name").getAsString();
                    String imagePath = ingredientObj.has("image") ? ingredientObj.get("image").getAsString() : "";
                    ingredients.add(new Ingredient(name, imagePath));
                }
            }

            Recipe recipe = new Recipe();
            recipe.setId(id);
            recipe.setTitle(title);
            recipe.setImage(image);
            recipe.setInstructions(instructions);
            recipe.setIngredients(ingredients);
            return recipe;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
