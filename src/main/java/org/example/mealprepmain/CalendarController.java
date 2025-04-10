package org.example.mealprepmain;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class CalendarController {
    @FXML
    private Label sundayLabel, mondayLabel, tuesdayLabel, wednesdayLabel, thursdayLabel, fridayLabel, saturdayLabel;
    @FXML
    private GridPane calendarGridPane;
    @FXML
    private TextArea sundayMealPlanTF, mondayMealPlanTF, tuesdayMealPlanTF, wednesdayMealPlanTF, thursdayMealPlanTF, fridayMealPlanTF, saturdayMealPlanTF;
    @FXML
    private Button addToPlanButton;
    @FXML
    private ComboBox<String> dayCB, mealTypeCB, selectedMealCB;
}
