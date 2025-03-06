module org.example.mealprepmain {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.mealprepmain to javafx.fxml;
    exports org.example.mealprepmain;
}