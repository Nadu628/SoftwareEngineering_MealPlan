package org.example.mealprepmain;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class RegistrationScreenController {

    @FXML
    private TextField firstNameTextField, lastNameTextField, emailTextField, birthdayTextField;
    @FXML
    private CheckBox vegetarianCheckBox, veganCheckBox, glutenFreeCheckBox, dairyFreeCheckBox, ketoCheckBox, paleoCheckBox,
    lowSodiumCheckBox, highProteinCheckBox, nutFreeCheckBox;
    @FXML
    Pane registrationPane;
    @FXML
    ImageView eatingImageView;

    private User user;

    @FXML
    public void initialize() {
        System.out.println("RegistrationScreenController initialized");
    }

    @FXML
    public void handleSubmit(){
        //collect food preferences
        List<String> preferences = new ArrayList<>();
        if(vegetarianCheckBox.isSelected()){
            preferences.add("vegetarian");
        }
        if(veganCheckBox.isSelected()){
            preferences.add("vegan");
        }
        if(glutenFreeCheckBox.isSelected()){
            preferences.add("glutenFree");
        }
        if(dairyFreeCheckBox.isSelected()){
            preferences.add("dairyFree");
        }
        if(ketoCheckBox.isSelected()){
            preferences.add("keto");
        }
        if(paleoCheckBox.isSelected()){
            preferences.add("paleo");
        }
        if(lowSodiumCheckBox.isSelected()){
            preferences.add("lowSodium");
        }
        if(highProteinCheckBox.isSelected()){
            preferences.add("highProtein");
        }
        if(nutFreeCheckBox.isSelected()){
            preferences.add("nutFree");
        }

        //user object
        user = new User(firstNameTextField.getText(), lastNameTextField.getText(), emailTextField.getText(), birthdayTextField.getText(), preferences);

        System.out.println("user: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("food preferences: " + user.getPreferences());
    }
}
