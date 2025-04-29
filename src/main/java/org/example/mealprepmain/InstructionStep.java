package org.example.mealprepmain;

import com.google.gson.annotations.SerializedName;

public class InstructionStep {
    @SerializedName("number")
    private int number;
    private String step;

    public InstructionStep(int number, String step) {
        this.number = number;
        this.step = step;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}
