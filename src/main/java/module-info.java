module org.example.mealprepmain {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.mealprepmain to javafx.fxml;
    exports org.example.mealprepmain;
}