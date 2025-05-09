package org.example.mealprepmain;

public class Ingredient {
    private String name; //name of ingredient
    private String image; //image url for ingredient

    public Ingredient(String name, String imagePath) {
        this.name = name;
        this.image = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
