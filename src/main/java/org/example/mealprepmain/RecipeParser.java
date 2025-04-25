package org.example.mealprepmain;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

    public Recipe parseRecipeDetails(String jsonResponse){
        try{
            JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);
            Recipe recipe = new Gson().fromJson(jsonResponse, Recipe.class);

            if(recipe.getInstructions() != null){
                System.out.println("recipe instructions found: " + recipe.getInstructions().size());
                for(RecipeInstructions instruction: recipe.getInstructions()){
                    if(instruction.getSteps() != null){
                        for(InstructionStep step : instruction.getSteps()){
                            System.out.println("step: " + step.getNumber() + ": " + step.getStep());
                        }
                    }else{
                        System.out.println("no steps found for instructions set");
                    }
                }
            }else{
                System.out.println("recipe instructions not found");
            }
            return recipe;
        }catch(Exception e){
            System.err.println("error parsing json response");
            e.printStackTrace();
            return null;
        }
    }
}
