module org.example.mealprepmain {
    requires javafx.controls;
    requires javafx.fxml;
    requires jbcrypt;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires com.google.gson;
    requires java.sql;
    requires java.base;


    opens org.example.mealprepmain to javafx.fxml, com.google.gson;
    exports org.example.mealprepmain;
}