package org.example.mealprepmain;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

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
}
