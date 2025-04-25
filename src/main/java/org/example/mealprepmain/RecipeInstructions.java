package org.example.mealprepmain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeInstructions {
    @SerializedName("steps")
    private List<InstructionStep> steps; //stores multiple instructions
    @SerializedName("name")
    private String name;

    public String getName(){
        return name;
    }
    public List<InstructionStep> getSteps(){
        return steps;
    }
    public void setSteps(List<InstructionStep> steps){
        this.steps = steps;
    }
    public void setName(String name){
        this.name = name;
    }


}
