module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;


    opens com.example.demo1 to javafx.fxml;
    opens com.example.demo1.Models to javafx.base, javafx.fxml;
    exports com.example.demo1;
}